package com.rosemak.dogcentralv106.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;
import com.rosemak.dogcentralv106.uifragment.DetailFragment;

/**
 * Created by stevierose on 11/29/15.
 */
public class DetailActivity extends AppCompatActivity implements DetailFragment.DetailListener {
    public static final String TAG = DetailActivity.class.getSimpleName();
    private GooglePlace mGooglePlace;
    private String name, vicinity, openNow;
    private Float rating;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            DetailFragment detailFragment = new DetailFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, detailFragment, DetailFragment.TAG)
                    .commit();


           /* MapFragment mapFragment = new MapFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_2, mapFragment, MapFragment.TAG)
                    .commit();*/
        }

        Intent getIntent = getIntent();
        if (getIntent.hasExtra("placeDetails")){
            mGooglePlace = (GooglePlace) getIntent.getSerializableExtra("placeDetails");
            Log.d(TAG, "place details= " + mGooglePlace.getName());
            name = mGooglePlace.getName();
            vicinity = mGooglePlace.getVicinity();
            openNow = mGooglePlace.getOpen();
            rating = mGooglePlace.getRating();
            Log.d(TAG, "rating= " + rating);

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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

    @Override
    public Float detailRating() {
        return rating;
    }
}

