package com.rosemak.dogcentralv110.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rosemak.dogcentralv110.places.FourSquarePlace;
import com.rosemak.dogcentralv110.R;

import java.util.ArrayList;

/**
 * Created by stevierose on 11/29/15.
 */
public class CategoriesArrayAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<FourSquarePlace> mArrayList;
    private Context mContext;

    public CategoriesArrayAdapter(Context _context, ArrayList<FourSquarePlace> _place){

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.foursquare_list_item, parent, false);
        }
        final FourSquarePlace fSPlace = (FourSquarePlace) getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.fSName);
        name.setText(fSPlace.getmName());

        return convertView;
    }
}
