package com.rosemak.dogcentralv110.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
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
        public String detailPhoneNum();

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
        TextView detailPhoneNum = (TextView) getActivity().findViewById(R.id.detailPlacePhoneNum);


        detailName.setText(mListener.detailName());
        detailVicinity.setText(mListener.detailVicinty().replaceAll("\\p{P}", ""));
        detailOpenNow.setText(mListener.detailPlaceOpen());
        if (mListener.detailPhoneNum() != null) {
            detailPhoneNum.setText(mListener.detailPhoneNum());
            final String num = mListener.detailPhoneNum();
            detailPhoneNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+num));
                    startActivity(callIntent);
                }
            });

        } else {
            detailPhoneNum.setText("no phone number listed");
        }


    }
}
