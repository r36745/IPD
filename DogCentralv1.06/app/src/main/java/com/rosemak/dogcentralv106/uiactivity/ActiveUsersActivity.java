package com.rosemak.dogcentralv106.uiactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.uifragment.ActiveUsersFragment;

/**
 * Created by stevierose on 12/4/15.
 */
public class ActiveUsersActivity extends AppCompatActivity {
    public static final String TAG = ActivitiesActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_users);

        ActiveUsersFragment activeUsersFragment = new ActiveUsersFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, activeUsersFragment, ActiveUsersFragment.TAG)
                .commit();
    }
}
