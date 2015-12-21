package com.rosemak.dogcentralv110.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.R;

import java.util.ArrayList;

/**
 * Created by stevierose on 12/4/15.
 */
public class SocialAdapter extends BaseAdapter {
    public static final String TAG = SocialAdapter.class.getSimpleName();

    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<GooglePlace> mArrayList;
    private final SparseBooleanArray mCollapsedStatus;
    private Context mContext;
    ExpandableTextView expandableTextView;

    public SocialAdapter(Context _context, ArrayList<GooglePlace> _place){

        mContext = _context;
        mArrayList =  _place;

        mCollapsedStatus = new SparseBooleanArray();
    }
    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_social_list_item, parent, false);
        }
        //ParseObject obj = (ParseObject) getItem(position);
            GooglePlace gPlace = new GooglePlace();
          gPlace = (GooglePlace) getItem(position);

        final ImageView img = (ImageView) convertView.findViewById(R.id.userImg);
        //final TextView textView = (TextView) convertView.findViewById(R.id.userView);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.usersName);
        final TextView dateTextView = (TextView) convertView.findViewById(R.id.usersDate);
        final TextView timeTextView = (TextView) convertView.findViewById(R.id.usersTime);
                expandableTextView = (ExpandableTextView) convertView.findViewById(R.id.expand_text_view);

        //expandableTextView.setText(gPlace.getmUserNotes(),mCollapsedStatus, position);



        ImageLoader imageLoader = ImageLoader.getInstance();
        if (imageLoader != null) {

            String imageUri = gPlace.getPhoto();
            final String notes = gPlace.getmUserNotes();
            final String theName = gPlace.getCurrentUser();
            final String date = gPlace.getmMonth() + "/" + gPlace.getmDay() + "/" + gPlace.getmYear();
            final String time = gPlace.getTime();
            ImageSize targetSize = new ImageSize(80, 50);
            imageLoader.displayImage(imageUri, img);
            imageLoader.loadImage(imageUri, targetSize, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // Do whatever you want with Bitmap
                    img.setImageBitmap(loadedImage);
                    //textView.setText(notes);
                    nameTextView.setText(theName);
                    dateTextView.setText(date);
                    timeTextView.setText(time);

                }
            });

            //textView.setText(notes);
            nameTextView.setText(theName);
            dateTextView.setText(date);
            timeTextView.setText(time);

        } else {
            Log.d(TAG, "Loader issue");
        }

        expandableTextView.setText(gPlace.getmUserNotes(),mCollapsedStatus, position);

        return convertView;
    }


}
