package com.rosemak.dogcentralv110.uiactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.ParseUser;
import com.rosemak.dogcentralv110.GooglePlace;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.uifragments.CategoriesFragment;
import com.rosemak.dogcentralv110.uifragments.DogAdoptionFragment;

public class MainActivity extends AppCompatActivity implements DogAdoptionFragment.OnAdoptionClick {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected Button mUpdateButton;
    private Uri mImageUri;
    protected ImageView mImageView;
    protected ImageButton mAddPhoto;
    public String KEY_NOTES = "notes";
    public String KEY_IMG = "image";
    public static final String POSTS = "AllPost";
    private static final int REQUEST_TAKE_PICTURE = 0x01001;
    public static final String ADOPTME = "adoptme";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        CategoriesFragment cf = new CategoriesFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, cf, CategoriesFragment.TAG)
                .commit();

        DogAdoptionFragment dogAdoptionFragment = new DogAdoptionFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container_2, dogAdoptionFragment, DogAdoptionFragment.TAG)
                .commit();


        /*mAddPhoto = (ImageButton) findViewById(R.id.photo);

        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getImageUri();
                if (mImageUri != null) {

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                }
                startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);

            }
        });*/


       /* ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();
        final ImageView imageView;
        imageView = (ImageView) findViewById(R.id.img);
        String imageUri = "http://photos.petfinder.com/photos/pets/33755650/1/?bust=1447251502&width=300&-pn.jpg";


        imageLoader.displayImage(imageUri, imageView);
        imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
                imageView.setImageBitmap(loadedImage);
            }
        });*/




        /*MyMapFragment map = new MyMapFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, map, MyMapFragment.TAG)
                .commit();*/





    }


    private void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        MainActivity.this.sendBroadcast(scanIntent);
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
