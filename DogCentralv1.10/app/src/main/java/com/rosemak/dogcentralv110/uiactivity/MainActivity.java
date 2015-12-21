package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uifragments.CategoriesFragment;
import com.rosemak.dogcentralv110.uifragments.DogAdoptionFragment;

public class MainActivity extends AppCompatActivity implements DogAdoptionFragment.OnAdoptionClick {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String ADOPTME = "adoptme";
    public UIHelper helper;
    Menu currentMenu;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);




        CategoriesFragment cf = new CategoriesFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, cf, CategoriesFragment.TAG)
                .commit();

        DogAdoptionFragment dogAdoptionFragment = new DogAdoptionFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container_2, dogAdoptionFragment, DogAdoptionFragment.TAG)
                .commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void dogList(GooglePlace dog) {

        Intent intent = new Intent(this, AdoptionDetailActivity.class);
        intent.putExtra(ADOPTME, dog);
        startActivity(intent);

    }

}
