package com.rosemak.dogcentralv110.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rosemak.dogcentralv110.R;

/**
 * Created by stevierose on 11/29/15.
 */
public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getSimpleName();
    private DetailListener mListener;


    public interface DetailListener{
        public String detailName();
        public String detailVicinty();
        public String detailPlaceOpen();
        public String detailRating();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DetailListener) {
            mListener = (DetailListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView detailName = (TextView) getActivity().findViewById(R.id.detailPlaceName);
        TextView detailVicinity = (TextView) getActivity().findViewById(R.id.detailPlaceVicinity);
        TextView detailOpenNow = (TextView) getActivity().findViewById(R.id.detailPlaceOpenNow);
        //RatingBar detailRating = (RatingBar) getActivity().findViewById(R.id.detailPlaceRating);


        detailName.setText(mListener.detailName());
        detailVicinity.setText(mListener.detailVicinty().replaceAll("\\p{P}",""));
        detailOpenNow.setText(mListener.detailPlaceOpen());
        //detailRating.setRating(mListener.detailRating());
    }
}
