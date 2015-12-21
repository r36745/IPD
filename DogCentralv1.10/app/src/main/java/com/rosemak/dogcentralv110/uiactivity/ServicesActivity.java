package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uifragments.ServicesListFragment;

/**
 * Created by stevierose on 11/30/15.
 */
public class ServicesActivity extends AppCompatActivity implements ServicesListFragment.SvcsOnButtonClickListener {
    public static final String TAG = ServicesActivity.class.getSimpleName();
    private UIHelper helper;
    private Menu currentMenu;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);


        helper = new UIHelper();
        if (!helper.isNetworkAvailable(ServicesActivity.this)){


            Intent intent = new Intent(ServicesActivity.this, MainActivity.class);
            startActivity(intent);


        } else {

            ServicesListFragment servicesListFragment = new ServicesListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, servicesListFragment, ServicesListFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void placesArray(GooglePlace placeArray) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("placeDetails", placeArray);
        startActivity(intent);

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
}
