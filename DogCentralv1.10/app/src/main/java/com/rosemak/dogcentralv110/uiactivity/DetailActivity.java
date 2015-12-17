package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.rosemak.dogcentralv110.places.FourSquarePlace;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uifragments.MyMapFragment;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.uifragments.DetailFragment;

import java.util.ArrayList;

/**
 * Created by stevierose on 11/29/15.
 */
public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailListener {
    public static final String TAG = DetailActivity.class.getSimpleName();
    private GooglePlace mGooglePlace;
    private FourSquarePlace mFSPlace;
    private String name, vicinity, openNow;
    private String rating;
    private double mLat;
    private double mLng;
    private ArrayList<GooglePlace>mArrayList;




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (savedInstanceState == null) {
            DetailFragment detailFragment = new DetailFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, detailFragment, DetailFragment.TAG)
                    .commit();


            /*MyMapFragment mapFragment = new MyMapFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_2, mapFragment)
                    .commit();*/
        }

        Intent getIntent = getIntent();
        if (getIntent.hasExtra("placeDetails")){
            mGooglePlace = (GooglePlace) getIntent.getSerializableExtra("placeDetails");
            Log.d(TAG, "place details= " + mGooglePlace.getName());
            name = mGooglePlace.getName();
            vicinity = mGooglePlace.getVicinity();
            openNow = mGooglePlace.getOpen();
            mLat = mGooglePlace.getmLat();
            mLng = mGooglePlace.getmLng();



            MyMapFragment myMapFragment = MyMapFragment.newInstance(mGooglePlace);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_2, myMapFragment)
                    .commit();

           /*MyMapFragment mapFragment = MyMapFragment.newInstance(mGooglePlace);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_2, mapFragment, MyMapFragment.TAG).commit();*/

        } else if(getIntent.hasExtra("fPlaceDetails")){
            mFSPlace = (FourSquarePlace) getIntent.getSerializableExtra("fPlaceDetails");
            Log.d(TAG, "has details");
            name = mFSPlace.getmName();
            openNow = mFSPlace.getmHereNow();
            vicinity = mFSPlace.getmAddress();
            mLat = mFSPlace.getmLat();
            mLng = mFSPlace.getmLng();
            double lat = mFSPlace.getmLat();
            double lng = mFSPlace.getmLng();
            Log.d(TAG, "lat= " + lat + "lng= " + lng);

            MyMapFragment myMapFragment = MyMapFragment.newInstance(mFSPlace);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_2, myMapFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.log_out) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Log.d(TAG, "Logged out");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


            return true;
        } else if (id == R.id.my_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public String detailName() {
        return name;
    }

    @Override
    public String detailVicinty() {
        return vicinity;
    }

    @Override
    public String detailPlaceOpen() {
        return openNow;
    }





}

