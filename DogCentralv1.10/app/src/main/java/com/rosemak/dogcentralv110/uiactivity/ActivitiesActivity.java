package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.places.FourSquarePlace;
import com.rosemak.dogcentralv110.uifragments.ActivitiesListFragment;

/**
 * Created by stevierose on 11/29/15.
 */
public class ActivitiesActivity extends AppCompatActivity implements ActivitiesListFragment.OnActivitiesClickListener {
    public static final String TAG = ActivitiesActivity.class.getSimpleName();
    public UIHelper helper;
    private Menu currentMenu;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activites);


        if (savedInstanceState == null) {

            helper = new UIHelper();
            if (!helper.isNetworkAvailable(ActivitiesActivity.this)){


                Intent intent = new Intent(ActivitiesActivity.this, MainActivity.class);
                startActivity(intent);

            } else {

                ActivitiesListFragment activitiesFragment = new ActivitiesListFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, activitiesFragment, ActivitiesListFragment.TAG)
                        .commit();
            }




        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        currentMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.my_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void activitiesArray(FourSquarePlace place) {

        Intent intent = new Intent(this, DetailActivity.class);
        String name = place.getmName();
        Log.d(TAG, "name= " + name);
        double lat = place.getmLat();
        double lng = place.getmLng();
        Log.d(TAG, "Lat= " + lat + "Lng= " + lng);
                intent.putExtra("fPlaceDetails", place);
                startActivity(intent);
    }
}
