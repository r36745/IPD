package com.rosemak.dogcentralv110.uifragments;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.rosemak.dogcentralv110.ArrayAdapter;
import com.rosemak.dogcentralv110.GooglePlace;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.UIHelper;

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
 * Created by stevierose on 11/30/15.
 */
public class HealthListFragment extends Fragment implements LocationListener {

    public static final String TAG = HealthListFragment.class.getSimpleName();
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
    private HealthOnButtonClickListener mListener;
    protected ProgressBar mProgressBar;
    private URL queryURL;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health, container, false);
    }

    public interface HealthOnButtonClickListener {

        public void placesArray(GooglePlace placeArray);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof HealthOnButtonClickListener) {
            mListener = (HealthOnButtonClickListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton refresh = (ImageButton) getActivity().findViewById(R.id.healthRefreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetStores().execute(queryURL);
            }
        });
        String API_KEY = "AIzaSyAdz3rwZeBojFyBItGwc0mmTBrPX8ZYtkg";
        mLocation = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //enableGps();
        UIHelper helper = new UIHelper();
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.VISIBLE);
        if (helper.isNetworkAvailable(getActivity())) {
            try {
                //mProgressBar.setVisibility(View.VISIBLE);
                enableGps();
                Log.d(TAG, "Lat= " + mLat + "Lng= " + mLng);
                String placesData = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + mLat + "," + mLng + "&radius=8000&types=pet_stores&keyword=dog|veterinary&key=" + API_KEY;
                queryURL = new URL(placesData);
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

                    if (jsonArray.getJSONObject(i).has("name")) {
                        gPlace.setName(placesObject.getString("name"));
                    }
                    if (jsonArray.getJSONObject(i).has("rating")) {
                        String rating = placesObject.getString("rating");

                        Float myFloat = Float.parseFloat(rating);
                        gPlace.setRating(myFloat);

                    }

                    if (jsonArray.getJSONObject(i).has("opening_hours")) {
                        if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {
                            String yes = gPlace.setOpen("Yes");
                            Log.d(TAG, yes);
                        } else {
                            String no = gPlace.setOpen("No");
                            Log.d(TAG, no);
                        }
                    } else {
                        String unknown = gPlace.setOpen("unknown");
                    }

                    if (jsonArray.getJSONObject(i).has("vicinity")) {
                        gPlace.setVicinity(placesObject.getString("vicinity"));
                    }

                    if (jsonArray.getJSONObject(i).getJSONObject("geometry").has("location")) {

                        String lat = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                        String lng = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                        double dLat = Double.parseDouble(lat);
                        double dLng = Double.parseDouble(lng);
                        gPlace.setmLat(dLat);
                        gPlace.setmLng(dLng);
                    }

                    mProgressBar.setVisibility(View.INVISIBLE);
                    arrayList.add(gPlace);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), arrayList);
                    ListView placesList = (ListView) getActivity().findViewById(R.id.list);
                    placesList.setAdapter(arrayAdapter);

                    placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
            //double lat = mArrayList.get(i).getmLat().getLatitude();
            mLat = loc.getLatitude();
            mLng = loc.getLongitude();
            Log.d(TAG, "lat= " +mLat);

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
        mLocation.removeUpdates((LocationListener) this);

    }


}
