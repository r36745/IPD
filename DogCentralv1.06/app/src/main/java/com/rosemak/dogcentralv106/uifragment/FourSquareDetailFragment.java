package com.rosemak.dogcentralv106.uifragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.places.GooglePlace;

import java.util.ArrayList;

/**
 * Created by stevierose on 12/6/15.
 */
public class FourSquareDetailFragment extends Fragment {
    public static final String TAG = FourSquareDetailFragment.class.getSimpleName();
    public GooglePlace mGooglePlace;
    private ArrayList<GooglePlace> mArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adoption, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView breed = (TextView) getActivity().findViewById(R.id.breedType);
        TextView age = (TextView) getActivity().findViewById(R.id.age);
        TextView email = (TextView) getActivity().findViewById(R.id.email);
        TextView phone = (TextView) getActivity().findViewById(R.id.phone);
        ImageView view = (ImageView) getActivity().findViewById(R.id.dogAdoptImg);

        GooglePlace gPlace = new GooglePlace();

        breed.setText(gPlace.getBreed());
        age.setText(gPlace.getAge());
        email.setText(gPlace.getEmail());
        phone.setText(gPlace.getPhone());



    }
}
