package com.rosemak.dogcentralv110;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;

import java.util.ArrayList;

/**
 * Created by stevierose on 12/4/15.
 */
public class SocialAdapter extends BaseAdapter {
    public static final String TAG = SocialAdapter.class.getSimpleName();

    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<GooglePlace> mArrayList;
    private Context mContext;

    public SocialAdapter(Context _context, ArrayList<GooglePlace> _place){

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_social_list_item, parent, false);
        }
        //ParseObject obj = (ParseObject) getItem(position);
            GooglePlace gPlace = new GooglePlace();
          gPlace = (GooglePlace) getItem(position);

        ImageView img = (ImageView) convertView.findViewById(R.id.userImg);
        TextView textView = (TextView) convertView.findViewById(R.id.userView);






        //img.setImageBitmap(BitmapFactory.decodeFile(uri.getPath()));
        textView.setText(gPlace.getmUserNotes());

        return convertView;
    }


}
