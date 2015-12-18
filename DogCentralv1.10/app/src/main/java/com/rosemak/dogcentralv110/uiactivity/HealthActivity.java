package com.rosemak.dogcentralv110.uiactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uifragments.HealthListFragment;
import com.rosemak.dogcentralv110.R;

/**
 * Created by stevierose on 11/30/15.
 */
public class HealthActivity extends AppCompatActivity implements HealthListFragment.HealthOnButtonClickListener {
    public static final String TAG = HealthActivity.class.getSimpleName();
    public UIHelper helper;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);


        helper = new UIHelper();
        if (!helper.isNetworkAvailable(HealthActivity.this)){


            Intent intent = new Intent(HealthActivity.this, MainActivity.class);
            startActivity(intent);

        } else {

            HealthListFragment healthListFragment = new HealthListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, healthListFragment, HealthListFragment.TAG)
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

        }  else if (id == R.id.log_in) {

            helper = new UIHelper();
            if (!helper.isNetworkAvailable(HealthActivity.this)){


                new AlertDialog.Builder(HealthActivity.this)
                        .setTitle("Wifi is unavailable")
                        .setMessage("Unable to login")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();

            } else {

                ParseLoginBuilder builder = new ParseLoginBuilder(HealthActivity.this);
                startActivityForResult(builder.build(), 0);

            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void placesArray(GooglePlace placeArray) {

        double lat = placeArray.getmLat();
        Log.d("LATTY", "THE LAT" + lat);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("placeDetails", placeArray);
        startActivity(intent);


    }
}
