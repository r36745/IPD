package com.rosemak.dogcentralv110;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;

import java.util.ArrayList;

/**
 * Created by stevierose on 11/26/15.
 */
public class ArrayAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<GooglePlace> mArrayList;
    private Context mContext;

    public ArrayAdapter(Context _context, ArrayList<GooglePlace> _place){

        mContext = _context;
        mArrayList =  _place;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.google_list_item, parent, false);
        }
        final GooglePlace gPlace = (GooglePlace) getItem(position);

        TextView lPlace = (TextView) convertView.findViewById(R.id.placeName);
        lPlace.setText(gPlace.getName());

        RatingBar lRating = (RatingBar) convertView.findViewById(R.id.placeRating);
        Float rating = gPlace.getRating();
        if (rating == null){
            Log.d("RATING NULL", "RATING NULL");

        } else {

            lRating.setRating(rating);
        }


        return convertView;

    }
}
