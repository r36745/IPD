package com.rosemak.dogcentralv106.uifragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.UIHelper;
import com.rosemak.dogcentralv106.adapters.CategoriesArrayAdapter;
import com.rosemak.dogcentralv106.places.FourSquarePlace;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by stevierose on 11/29/15.
 */
public class ActivitiesListFragment extends Fragment implements LocationListener {
    private double mLat;
    private double mLng;
    private LocationManager mLocation;
    private static final int REQUEST_ENABLE_GPS = 0x02001;
    private FourSquarePlace mFSPlace;
    public static final String TAG = ActivitiesListFragment.class.getSimpleName();
    private JSONObject fSObject;
    private JSONArray jsonArray;
    private JSONObject dataObject;
    private JSONObject jsonObject;
    ArrayList<FourSquarePlace> arrayList;


    public ActivitiesListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocation = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activities, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String CLIENT_ID = "WUFIT5BFCDUCWBJPI3PR5EGQWGONDD2ZSQD1E31SC0C4EHLL";
        final String CLIENT_SECRET = "TMUAJ3EG1ULQN0DPXUVDMVJTCCJ5MY2LXRD1GPTYL2UXWJY1";
        UIHelper helper = new UIHelper();



        if (helper.isNetworkAvailable(getActivity())) {
            try {
                enableGps();
                arrayList= new ArrayList<>();
                Log.d("Activites Location", "Lat= " + mLat + "Lng= " + mLng);
                String fSURL = "https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815%20&ll=" + mLat + "," + mLng + "%20&query=dog%20activities";

                URL queryURL = new URL(fSURL);
                new GetActivities().execute(queryURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }


    }


    public class GetActivities extends AsyncTask<URL, Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(URL... urls) {

            String jsonString = "";

            for (URL queryURL : urls) {
                try {
                    URLConnection conn = queryURL.openConnection();
                    jsonString = IOUtils.toString(conn.getInputStream());
                    break;
                } catch (IOException e) {
                    Log.e(TAG, "Could not establish URLConnection to " + queryURL.toString());
                }
            }
            Log.i(TAG, "Activities Received Data=  " + jsonString);
            JSONObject fSData;

            try {
                fSData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
                fSData = null;
            }


            return fSData;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {



            try {
                fSObject = jsonObject;

                if (fSObject.has("response")){
                    if (fSObject.getJSONObject("response").has("venues")){
                        final FourSquarePlace fSPlace = new FourSquarePlace();
                        JSONArray obj = fSObject.getJSONObject("response").getJSONArray("venues");

                        for (int i=0; i<obj.length(); i++){
                            dataObject = obj.getJSONObject(i);
                            if (obj.getJSONObject(i).has("name")){
                                String name = obj.getJSONObject(i).getString("name");
                                Log.d("NAME", "THE NAME= " +name);
                                //fSPlace.setmName(name);
                                fSPlace.setmName(obj.getJSONObject(i).getString("name"));
                            }

                            if (obj.getJSONObject(i).getJSONObject("location").has("formattedAddress")){
                                String address = obj.getJSONObject(i).getJSONObject("location").getString("formattedAddress");
                                Log.d("Formatted", "Formatted address= " +address);
                                fSPlace.setmAddress(address);
                            }
                            if (obj.getJSONObject(i).getJSONObject("hereNow").has("summary")){
                                String summary = obj.getJSONObject(i).getJSONObject("hereNow").getString("summary");
                                Log.d("SUMMARY", "We Finally have a summary= " + summary);
                                fSPlace.setmHereNow(summary);

                            arrayList.add(fSPlace);



                            }

                        }

                        ListView list = (ListView) getActivity().findViewById(R.id.activitiesList);
                        CategoriesArrayAdapter adapter = new CategoriesArrayAdapter(getActivity(), arrayList);
                        list.setAdapter(adapter);





                    }

                }






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void enableGps() {
        if (mLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location loc = mLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mLat = loc.getLatitude();
            mLng = loc.getLongitude();
            Log.d("Locale", "lat= " + mLat + "Lng= " + mLng);
           // mFSPlace.setmLat(mLat);
           // mFSPlace.setmLng(mLng);

        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("GPS Unavailable")
                    .setMessage("Please enable GPS in the system settings.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }

                    })
                    .show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        enableGps();
    }


    @Override
    public void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mLocation.removeUpdates((LocationListener) getActivity());

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
