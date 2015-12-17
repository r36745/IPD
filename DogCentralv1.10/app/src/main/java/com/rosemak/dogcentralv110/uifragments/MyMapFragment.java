package com.rosemak.dogcentralv110.uifragments;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rosemak.dogcentralv110.places.FourSquarePlace;
import com.rosemak.dogcentralv110.places.GooglePlace;

import java.util.ArrayList;

/**
 * Created by stevierose on 12/7/15.
 */
public class MyMapFragment extends MapFragment implements GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener, LocationListener {

    public static final String TAG = "MyMapFragment.TAG";
    private ArrayList<GooglePlace> mArrayList;
    private GooglePlace gPlace;
    private FourSquarePlace fPlace;
    private static final String PLACES = "places";
    private GoogleMap mMap;
    private double mLat;
    private double mLng;
    private double fLat;
    private double fLng;
    private String name;
    protected double gLat = 38.94048168;
    protected double gLng = -76.82931342;

    public static MyMapFragment newInstance(GooglePlace gPlace) {
        MyMapFragment myMapFragment = new MyMapFragment();
        double lat = gPlace.getmLat();
        double lng = gPlace.getmLng();
        Log.d(TAG, "lat= " +lat + "lng= " + lng);
        Bundle args = new Bundle();
        args.putSerializable(PLACES, gPlace);
        myMapFragment.setArguments(args);

        return myMapFragment;
    }

    public static MyMapFragment newInstance(FourSquarePlace fPlace) {
        MyMapFragment myMapFragment = new MyMapFragment();
        Bundle args = new Bundle();
        double lat = fPlace.getmLat();
        double lng = fPlace.getmLng();
        Log.d(TAG, "Lat= " + lat + "Lng= " + lng);
        args.putSerializable("FPLACES", fPlace);
        myMapFragment.setArguments(args);
        return myMapFragment;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(PLACES)) {
            gPlace = (GooglePlace) args.getSerializable(PLACES);
            double lat = gPlace.getmLat();
            double lng = gPlace.getmLng();
            Log.d(TAG, "lat= " + lat + "lng= " + lng);

            mLat = gPlace.getmLat();
            mLng = gPlace.getmLng();
            name = gPlace.getName();




        } else if(args != null && args.containsKey("FPLACES")) {
            fPlace = (FourSquarePlace) args.getSerializable("FPLACES");
            double lat = fPlace.getmLat();
            double lng = fPlace.getmLng();
            Log.d(TAG, "Lat= " +lat + "Lng= " + lng);

            mLat = fPlace.getmLat();
            mLng = fPlace.getmLng();
            name = fPlace.getmName();
        }

        mMap = getMap();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mLat, mLng))
                .title(name));

        mMap.setInfoWindowAdapter(new MarkerAdapter());
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLng), 16));


    }



    @Override
    public void onInfoWindowClick(final Marker marker) {
        Log.d(TAG, "marker click");


    }

    @Override
    public void onMapClick(final LatLng location) {


        double lat = location.latitude;
        double lng = location.longitude;
        Log.d(TAG, "Lat= " + lat + "Lng= " + lng);

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);


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


    private class MarkerAdapter implements GoogleMap.InfoWindowAdapter {

        TextView mText;

        public MarkerAdapter() {
            mText = new TextView(getActivity());
        }

        @Override
        public View getInfoContents(Marker marker) {
            mText.setText(marker.getTitle());
            return mText;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
    }

}
