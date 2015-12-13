package com.rosemak.dogcentralv110;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;

/**
 * Created by stevierose on 12/2/15.
 */
public class ParseInitializationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //Parse.enableLocalDatastore(this);
        Parse.initialize(this, "9Nled3xJ0fJnKSusId9EcUsoCtiOlFlZg1ZQ6BYz", "fkXFAkpfBV1dJSsbshJloU7GoGPFU4AC744w7SeS");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

    }


}
