package com.rosemak.dogcentralv110.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rosemak.dogcentralv110.GooglePlace;
import com.rosemak.dogcentralv110.R;

import java.util.ArrayList;

/**
 * Created by stevierose on 12/6/15.
 */
public class DogAdoptionAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<GooglePlace> mArrayList;
    private Context mContext;

    public DogAdoptionAdapter(Context _context, ArrayList<GooglePlace> _place){
        mContext = _context;
        mArrayList = _place;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adoption_list_item, parent, false);
        }

        GooglePlace gPlace = new GooglePlace();
        gPlace = (GooglePlace) getItem(position);

        TextView breed = (TextView) convertView.findViewById(R.id.breed);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        breed.setText(gPlace.getBreed());
        description.setText(gPlace.getAdoptedDogDescription());

        return convertView;
    }
}
