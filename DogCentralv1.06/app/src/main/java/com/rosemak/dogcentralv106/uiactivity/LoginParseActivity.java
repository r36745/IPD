package com.rosemak.dogcentralv106.uiactivity;

import android.app.Activity;
import android.os.Bundle;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.uifragment.LoginParseFragment;

/**
 * Created by stevierose on 12/7/15.
 */
public class LoginParseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parse);
        LoginParseFragment lpf = new LoginParseFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, lpf, LoginParseFragment.TAG)
                .commit();
    }
}
