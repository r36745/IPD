package com.rosemak.dogcentralv106.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;
import com.rosemak.dogcentralv106.uifragment.HealthListFragment;

/**
 * Created by stevierose on 11/30/15.
 */
public class HealthActivity extends AppCompatActivity implements HealthListFragment.HealthOnButtonClickListener{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        HealthListFragment healthListFragment = new HealthListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, healthListFragment, HealthListFragment.TAG)
                .commit();
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


        return super.onOptionsItemSelected(item);
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
