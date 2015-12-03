package com.rosemak.dogcentralv106.uifragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.uiactivity.PostActivity;

/**
 * Created by stevierose on 12/2/15.
 */
public class SocialFragment extends Fragment {

    public static final String TAG = SocialFragment.class.getSimpleName();
    private ImageButton addPost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState== null){


        }




    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //current user has been cached and can continue

            addPost = (ImageButton) getActivity().findViewById(R.id.addPostButton);
            addPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            //need to log in
            ParseLoginBuilder builder = new ParseLoginBuilder(getActivity());
            startActivityForResult(builder.build(), 0);
        }


    }
}
