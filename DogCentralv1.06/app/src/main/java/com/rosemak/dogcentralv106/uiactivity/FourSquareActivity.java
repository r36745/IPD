package com.rosemak.dogcentralv106.uiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.FourSquarePlace;
import com.rosemak.dogcentralv106.uifragment.FourSquareDetailFragment;

/**
 * Created by stevierose on 12/5/15.
 */
public class FourSquareActivity extends Activity implements FourSquareDetailFragment.OnFourSquareClick{
    public static final String TAG = FourSquareActivity.class.getSimpleName();
    private FourSquarePlace fSPlace;
    String name;
    String address;
    String summary;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_foursquare);

        FourSquareDetailFragment adf = new FourSquareDetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, adf, FourSquareDetailFragment.TAG)
                .commit();

        Intent getIntent = getIntent();
        if (getIntent.hasExtra("fPlaceDetails")){
            fSPlace = (FourSquarePlace) getIntent.getSerializableExtra("fPlaceDetails");

            name = fSPlace.getmName();
            Log.d(TAG, "Foursquarename= " +name);
            address = fSPlace.getmAddress();
            summary = fSPlace.getmHereNow();
        }



    }

    @Override
    public String fSDetailName() {
        return name;
    }

    @Override
    public String fSDetailAddress() {
        return address;
    }

    @Override
    public String fSDetailSummary() {
        return summary;
    }
}
