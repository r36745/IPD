package com.rosemak.dogcentralv106.uifragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.uiactivity.ActivitiesActivity;
import com.rosemak.dogcentralv106.uiactivity.HealthActivity;
import com.rosemak.dogcentralv106.uiactivity.ServicesActivity;

/**
 * Created by stevierose on 11/29/15.
 */
public class CategoriesFragment extends Fragment {

    ImageButton aButton, hButton, sButton, socialButton;
    public static final String TAG = CategoriesFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        aButton = (ImageButton) getActivity().findViewById(R.id.activity);
        hButton = (ImageButton) getActivity().findViewById(R.id.health);
        sButton = (ImageButton) getActivity().findViewById(R.id.services);


        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ActivitiesActivity.class);
                startActivity(intent);
            }
        });

        hButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthActivity.class);
                startActivity(intent);
            }
        });

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ServicesActivity.class);
                startActivity(intent);
            }
        });

    }
}
