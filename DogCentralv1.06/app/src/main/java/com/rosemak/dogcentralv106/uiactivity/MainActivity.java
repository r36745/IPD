package com.rosemak.dogcentralv106.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ui.ParseLoginBuilder;
import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;
import com.rosemak.dogcentralv106.uifragment.CategoriesFragment;
import com.rosemak.dogcentralv106.uifragment.PlacesListFragment;

public class MainActivity extends AppCompatActivity implements PlacesListFragment.OnButtonClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if (savedInstanceState == null){
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "e9YxQzIudswNTuHt8Z61Y6vj9G16UZ2xaM0zuXZH", "V89mKRxkAt9pkw7MFN4BBmrrjR47oQ9ZZs45Juy7");


            CategoriesFragment cFragment = new CategoriesFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, cFragment, CategoriesFragment.TAG)
                    .commit();



            PlacesListFragment pLFragment = new PlacesListFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_2,pLFragment, PlacesListFragment.TAG)
                    .commit();
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

        if (id == R.id.new_login) {
            ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
            startActivityForResult(builder.build(), 0);


            return true;
        } else if (id == R.id.my_home) {


        } else if (id==R.id.my_renew){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void placesPos(int pos) {

    }

    @Override
    public void placesArray(GooglePlace placeArray) {

        Log.d(TAG, "place name= " + placeArray.getName());
        Log.d(TAG, "rating= " + placeArray.getRating());
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("placeDetails", placeArray);
        startActivity(intent);

    }
}
