package com.rosemak.dogcentralv110.uifragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rosemak.dogcentralv110.HealthActivity;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.ServicesActivity;
import com.rosemak.dogcentralv110.SocialActivity;
import com.rosemak.dogcentralv110.uiactivity.ActivitiesActivity;

/**
 * Created by stevierose on 12/11/15.
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
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aButton = (ImageButton) getActivity().findViewById(R.id.activity);
        hButton = (ImageButton) getActivity().findViewById(R.id.health);
        sButton = (ImageButton) getActivity().findViewById(R.id.services);
        socialButton = (ImageButton) getActivity().findViewById(R.id.social);

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

        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent social = new Intent(getActivity(), SocialActivity.class);
                startActivity(social);
            }
        });


       /* ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Come and Be Social")
                    .setMessage("Log in to be Dog Social")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast cant be social without loggin in
                            Toast.makeText(getActivity(), "Come join the social world", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ParseLoginBuilder builder = new ParseLoginBuilder(getActivity());
                            startActivityForResult(builder.build(), 0);

                        }
                    }).show();




        } else {

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

            socialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent parseIntent = new Intent(getActivity(), LoginParseActivity.class);
                    startActivity(parseIntent);
                }
            });

        }*/


    }


}
