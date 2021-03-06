package com.rosemak.dogcentralv106.uifragment;

import android.Manifest;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.UIHelper;
import com.rosemak.dogcentralv106.adapters.ArrayAdapter;
import com.rosemak.dogcentralv106.places.GooglePlace;

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
public class PlacesListFragment extends Fragment implements LocationListener {

    public static final String TAG = PlacesListFragment.class.getSimpleName();
    public double mLat;
    public double mLng;
    private LocationManager mLocation;
    private static final int REQUEST_ENABLE_GPS = 0x02001;
    private GooglePlace gPlace;
    private JSONObject gPlacesData;
    private JSONArray jsonArray;
    private String status;
    private JSONObject placesObject;
    protected double gLat = 38.94048168;
    protected double gLng = -76.82931342;
    private OnButtonClickListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_places, container, false);
    }

    public interface OnButtonClickListener{

        public void placesPos(int pos);
        public void placesArray(GooglePlace placeArray);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnButtonClickListener){
            mListener = (OnButtonClickListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocation = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String API_KEY = "AIzaSyDAoJFZ0PsLPN602WpV98uWFnlyVsytHxc";
        UIHelper helper = new UIHelper();

        if (helper.isNetworkAvailable(getActivity())){
            try {
                //enableGps();
                Log.d("Location", "Lat= " + mLat + "Lng= " +mLng);
                String placesData = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + mLat +  "," +  mLng +"&radius=5000&types=pet_stores&keyword=grooming|dog&key=" + API_KEY;
                URL queryURL = new URL(placesData);
                new GetStores().execute(queryURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }

    private class GetStores extends AsyncTask<URL, Void, JSONObject> {

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
            Log.i(TAG, "Received Data=  " + jsonString);
            JSONObject placesData;

            try {
                placesData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
                placesData = null;
            }


            return placesData;
        }

        @Override
        protected void onPostExecute(JSONObject result) {


            final ArrayList<GooglePlace> arrayList = new ArrayList<GooglePlace>();

            try {
                gPlacesData = result;

                jsonArray = gPlacesData.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    final GooglePlace gPlace = new GooglePlace();
                    placesObject = jsonArray.getJSONObject(i);


                    String vicinity = placesObject.getString("vicinity");
                    Log.d(TAG, "vicinity= " + vicinity);


                    if (jsonArray.getJSONObject(i).has("name")){
                        gPlace.setName(placesObject.getString("name"));
                    }
                    if (jsonArray.getJSONObject(i).has("rating")){
                        String rating = placesObject.getString("rating");

                            Log.d(TAG, "my Rating2= " + rating);
                            Float myFloat = Float.parseFloat(rating);
                            Log.d("FLoatty", "My Floaty= " + myFloat);
                            gPlace.setRating(myFloat);

                    }

                    if (jsonArray.getJSONObject(i).has("opening_hours")) {
                        if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")){
                          String yes = gPlace.setOpen("Yes");
                            Log.d(TAG, yes);
                        } else {
                            String no = gPlace.setOpen("No");
                            Log.d(TAG, no);
                        }
                    } else {
                        String unknown = gPlace.setOpen("unknown");
                        Log.d(TAG, unknown);
                    }

                    if (jsonArray.getJSONObject(i).has("vicinity")){
                        gPlace.setVicinity(placesObject.getString("vicinity"));
                    }


                    arrayList.add(gPlace);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), arrayList);
                    ListView placesList = (ListView) getActivity().findViewById(R.id.list);
                    placesList.setAdapter(arrayAdapter);

                    placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Log.d(TAG, "Name= " +  arrayList.get(position).getName());
                            mListener.placesArray(arrayList.get(position));

                        }
                    });





                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            Log.d("LOCATION", "Lat= " + mLat + "Lng= " + mLng);


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
        mLocation.removeUpdates((android.location.LocationListener) this);

    }
}
