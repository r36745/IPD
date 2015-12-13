package com.rosemak.dogcentralv110;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;
import com.rosemak.dogcentralv106.uifragment.AdoptionDetailFragment;

import java.net.URL;

/**
 * Created by stevierose on 12/6/15.
 */
public class AdoptionDetailActivity extends Activity implements AdoptionDetailFragment.OnDogAdoptionClick{
    public static final String TAG = AdoptionDetailActivity.class.getSimpleName();
    private GooglePlace mGPlace;
    private String breed;
    private String description;
    private String url;
    private Bitmap img;
    private URL imgUrl;



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
            Log.d(TAG, "url= " + url);

        }

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
