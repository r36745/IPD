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
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.uifragments.PostFragment;
import com.rosemak.dogcentralv110.R;

/**
 * Created by stevierose on 12/11/15.
 */
public class PostActivity extends AppCompatActivity {
    public static final String TAG = PostActivity.class.getSimpleName();
    public UIHelper helper;
    private MenuItem login;
    private MenuItem logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        PostFragment postFragment = new PostFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, postFragment, PostFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_list, menu);

        login = menu.findItem(R.id.log_in);
        logout = menu.findItem(R.id.log_out);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.log_out) {
            logout.setVisible(false);
            login.setVisible(true);
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Log.d(TAG, "Logged out");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


            return true;
        } else if (id == R.id.my_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }  else if (id == R.id.log_in) {
            login.setVisible(false);
            logout.setVisible(true);

            helper = new UIHelper();
            if (!helper.isNetworkAvailable(PostActivity.this)){

                ParseLoginBuilder builder = new ParseLoginBuilder(PostActivity.this);
                startActivityForResult(builder.build(), 0);

            } else {

                new AlertDialog.Builder(PostActivity.this)
                        .setTitle("Wifi is unavailable")
                        .setMessage("Unable to login")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

}
