package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uifragments.AdoptionDetailFragment;

/**
 * Created by stevierose on 12/6/15.
 */
public class AdoptionDetailActivity extends AppCompatActivity implements AdoptionDetailFragment.OnDogAdoptionClick {
    public static final String TAG = AdoptionDetailActivity.class.getSimpleName();
    private GooglePlace mGPlace;
    private String breed;
    private String description;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption);

        AdoptionDetailFragment adf = new AdoptionDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, adf, AdoptionDetailFragment.TAG)
                .commit();

        Intent getIntent = getIntent();
        if (getIntent.hasExtra(MainActivity.ADOPTME)) {
            mGPlace = (GooglePlace) getIntent.getSerializableExtra(MainActivity.ADOPTME);
            breed = mGPlace.getBreed();
            description = mGPlace.getAdoptedDogDescription();
            url = mGPlace.getAdoptionPhoto();


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


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


            return true;
        } else if (id == R.id.my_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.log_in) {

            ParseLoginBuilder builder = new ParseLoginBuilder(AdoptionDetailActivity.this);
            startActivityForResult(builder.build(), 0);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public String breedName() {
        return breed;
    }

    @Override
    public String breedDescription() {
        return description;
    }

    @Override
    public String breedImg() {
        return url;
    }


}
