package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.uifragments.SettingsFragment;

/**
 * Created by stevierose on 12/19/15.
 */
public class SettingsActivity extends AppCompatActivity {
    Menu currentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsFragment sf = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, sf, SettingsFragment.TAG)
                .commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_list, menu);

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
        if (id == R.id.log_out) {

            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Log.d("TAG", "Logged out");

            currentMenu.findItem(R.id.log_out).setVisible(false);
            currentMenu.findItem(R.id.log_in).setVisible(true);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


            return true;
        } else if(id == R.id.action_settings) {

            Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.my_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
