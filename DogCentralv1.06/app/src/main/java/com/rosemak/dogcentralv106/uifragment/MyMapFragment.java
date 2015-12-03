package com.rosemak.dogcentralv106.uifragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rosemak.dogcentralv106.places.GooglePlace;

import java.util.ArrayList;

public class MyMapFragment extends MapFragment implements OnInfoWindowClickListener, OnMapClickListener, GoogleMap.OnMapLongClickListener {
    public static final String TAG = "MyMapFragment.TAG";
    private ArrayList<GooglePlace> mArrayList;
	private GooglePlace gPlace;
	private GoogleMap mMap;
	private double mLat;
	private double mLng;
	private String name;


	public static MyMapFragment newInstance(GooglePlace mGooglePlace) {

		MyMapFragment myMapFragment = new MyMapFragment();
		double lat = mGooglePlace.getmLat();
		Log.d(TAG, "Map lat= " + lat);
		Bundle args = new Bundle();
		args.putSerializable("PLACES", mGooglePlace);
		myMapFragment.setArguments(args);

		return myMapFragment;
	}



    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Bundle args = getArguments();
		if (args != null && args.containsKey("PLACES")) {
			gPlace = (GooglePlace) args.getSerializable("PLACES");
			mLat = gPlace.getmLat();
			mLng = gPlace.getmLng();
			name = gPlace.getName();

		}

        mMap = getMap();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mLat, mLng))
                .title(name));

        mMap.setInfoWindowAdapter(new MarkerAdapter());
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLng), 16));


    }


    @Override
    public void onMapLongClick(LatLng latLng) {



    }

	@Override
	public void onInfoWindowClick(final Marker marker) {
		Log.d(TAG, "marker click");

	}

	@Override
	public void onMapClick(final LatLng location) {

	}



	private class MarkerAdapter implements InfoWindowAdapter {
		
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