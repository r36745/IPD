package com.rosemak.dogcentralv106;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by stevierose on 12/2/15.
 */
public class ParseInitializationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "e9YxQzIudswNTuHt8Z61Y6vj9G16UZ2xaM0zuXZH", "V89mKRxkAt9pkw7MFN4BBmrrjR47oQ9ZZs45Juy7");

    }
}
