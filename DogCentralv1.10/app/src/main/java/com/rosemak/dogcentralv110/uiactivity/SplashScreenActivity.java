package com.rosemak.dogcentralv110.uiactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;
import com.rosemak.dogcentralv110.R;

/**
 * Created by stevierose on 10/21/15.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //If user remains logged in, skip splash screen
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);

        } else {

            Thread timerThread = new Thread(){
                public void run(){
                    try {
                        sleep(3000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            };
            timerThread.start();
        }


    }
}
