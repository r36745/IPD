package com.rosemak.dogcentralv106.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;
import com.rosemak.dogcentralv106.uifragment.ServicesListFragment;

/**
 * Created by stevierose on 11/30/15.
 */
public class ServicesActivity extends AppCompatActivity implements ServicesListFragment.SvcsOnButtonClickListener {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        ServicesListFragment servicesListFragment = new ServicesListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, servicesListFragment, ServicesListFragment.TAG)
                .commit();
    }

    @Override
    public void placesPos(int pos) {

    }

    @Override
    public void placesArray(GooglePlace placeArray) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("placeDetails", placeArray);
        startActivity(intent);

    }
}
