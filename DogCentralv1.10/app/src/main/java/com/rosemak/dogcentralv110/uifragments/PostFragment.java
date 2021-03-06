package com.rosemak.dogcentralv110.uifragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.UIHelper;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uiactivity.SocialActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by stevierose on 12/11/15.
 */
public class PostFragment extends Fragment {

    public static final String TAG = PostFragment.class.getSimpleName();
    protected EditText mNotes;
    protected Button mUpdateButton;
    private Uri mImageUri;
    protected ImageView mImageView;
    protected ImageButton mAddPhoto;
    private GooglePlace mGooglePlace;
    public String KEY_NOTES = "notes";
    public String KEY_IMG = "image";
    public String KEY_NAME = "usersname";
    public String KEY_DAY = "monthday";
    public String KEY_MONTH = "monthyear";
    public String KEY_YEAR = "yearyear";
    public String KEY_TIME = "timestamp";


    public static final String POSTS = "TestPost";
    private static final int REQUEST_TAKE_PICTURE = 0x01001;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImageView = (ImageView) getActivity().findViewById(R.id.previewImg);


        //Images for post
        if(requestCode == REQUEST_TAKE_PICTURE ) {
            if(mImageUri != null) {
                mImageView.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));

                addImageToGallery(mImageUri);


            } else {
                mImageView.setImageBitmap((Bitmap)data.getParcelableExtra("data"));
            }
        }


    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
        };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //save data if user is offline
        UIHelper helper = new UIHelper();
        mNotes = (EditText) getActivity().findViewById(R.id.editText);
        mUpdateButton = (Button) getActivity().findViewById(R.id.updateButton);
        mAddPhoto = (ImageButton) getActivity().findViewById(R.id.addPhoto);

        if (helper.isNetworkAvailable(getActivity())) {

            mAddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mImageUri = getImageUri();
                    if (mImageUri != null) {

                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    } else {
                        Log.d(TAG, "No image captured");
                    }
                    startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);
                }
            });


            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userNotes = mNotes.getText().toString();
                    String userImg = String.valueOf(mImageUri);


                    ParseObject post = new ParseObject(POSTS);
                    ParseUser user = ParseUser.getCurrentUser();

                    //get current date
                    Calendar calendar = Calendar.getInstance();
                    int cDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int cMonth = calendar.get(Calendar.MONTH) +1;
                    int year = calendar.get(Calendar.YEAR);

                    post.put(KEY_DAY, cDay);
                    post.put(KEY_MONTH, cMonth);
                    post.put(KEY_YEAR, year);


                    //get timestamp

                    TimeZone tz = TimeZone.getDefault();
                    SimpleDateFormat date = new SimpleDateFormat("KK:mm a");
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
                    Date currentLocalTime = cal.getTime();


                    Log.d(TAG, "Timezone= " + tz.getDisplayName() + "Timezone id = " + tz.getID());
                    date.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
                    String localTime = date.format(currentLocalTime);
                    Log.d(TAG, "time= " + localTime);
                    post.put(KEY_TIME, localTime);



                    String name = user.getString("name");
                    Log.d(TAG, "NAMES= " + name);
                    post.put(KEY_NAME, name);

                    if (!userNotes.equals("") && userImg.equals("") ) {
                        post.put(KEY_NOTES, userNotes);
                        Log.d(TAG, "post entered");
                        mNotes.setText("");

                    } else if (!userImg.equals("") && userNotes.equals("")){
                        post.put(KEY_IMG, userImg);
                        Log.d(TAG, "Image entered");


                    } else {
                        Log.d(TAG, "Image and post entered");
                        post.put(KEY_NOTES, userNotes);
                        post.put(KEY_IMG, userImg);

                    }


                    post.saveInBackground();
                    Intent intent = new Intent(getActivity(), SocialActivity.class);
                    startActivity(intent);

                }
            });

        } else {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Wifi is unavailable")
                    .setMessage("Post will be saved")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    }).show();


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
            });


            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userNotes = mNotes.getText().toString();
                    String userImg = String.valueOf(mImageUri);

                    ParseObject post = new ParseObject(POSTS);
                    ParseUser user = ParseUser.getCurrentUser();

                    //get current date
                    Calendar calendar = Calendar.getInstance();
                    int cDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int cMonth = calendar.get(Calendar.MONTH) +1;
                    int year = calendar.get(Calendar.YEAR);

                    post.put(KEY_DAY, cDay);
                    post.put(KEY_MONTH, cMonth);
                    post.put(KEY_YEAR, year);

                    //get timestamp

                    TimeZone tz = TimeZone.getDefault();
                    SimpleDateFormat date = new SimpleDateFormat("KK:mm a");
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
                    Date currentLocalTime = cal.getTime();


                    Log.d(TAG, "Timezone= " + tz.getDisplayName() + "Timezone id = " + tz.getID());
                    date.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
                    String localTime = date.format(currentLocalTime);
                    Log.d(TAG, "time= " + localTime);
                    post.put(KEY_TIME, localTime);

                    //Current user
                    String name = user.getString("name");
                    Log.d(TAG, "NAMES= " + name);
                    post.put(KEY_NAME, name);

                    if (!userNotes.equals("") && userImg.equals("")) {
                        post.put(KEY_NOTES, userNotes);
                        Log.d(TAG, "post entered");
                        mNotes.setText("");

                    } else if (!userImg.equals("") && userNotes.equals("")) {
                        post.put(KEY_IMG, userImg);
                        Log.d(TAG, "Image entered");


                    } else {
                        Log.d(TAG, "Image and post entered");
                        post.put(KEY_NOTES, userNotes);
                        post.put(KEY_IMG, userImg);

                    }


                    post.saveEventually();
                    Intent intent = new Intent(getActivity(), SocialActivity.class);
                    startActivity(intent);

                }
            });
        }



    }

    private Uri getImageUri() {

        String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));

        File imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appDir = new File(imageDir, "CameraExample");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        try {
            image.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return Uri.fromFile(image);
    }

    private void addImageToGallery(Uri imageUri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        getActivity().sendBroadcast(scanIntent);
    }

    public void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH) +1;
        int year = calendar.get(Calendar.YEAR);


    }
}
