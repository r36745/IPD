package com.rosemak.dogcentralv110.uiactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.uifragments.SocialFragment;

/**
 * Created by stevierose on 12/11/15.
 */
public class SocialActivity extends AppCompatActivity {

    public static final String TAG = SocialActivity.class.getSimpleName();
    public UIHelper helper;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        helper = new UIHelper();
        if (!helper.isNetworkAvailable(SocialActivity.this)){

            Intent intent = new Intent(SocialActivity.this, MainActivity.class);
            startActivity(intent);

        } else {

            SocialFragment socialFragment = new SocialFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, socialFragment, SocialFragment.TAG)
                    .commit();
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
            Log.d(TAG, "Logged out");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


            return true;
        } else if (id == R.id.my_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id==R.id.log_in){

            helper = new UIHelper();
            if (!helper.isNetworkAvailable(SocialActivity.this)){



                new AlertDialog.Builder(SocialActivity.this)
                        .setTitle("Wifi is unavailable")
                        .setMessage("Unable to login")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();

            } else {

                ParseLoginBuilder builder = new ParseLoginBuilder(SocialActivity.this);
                startActivityForResult(builder.build(), 0);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
