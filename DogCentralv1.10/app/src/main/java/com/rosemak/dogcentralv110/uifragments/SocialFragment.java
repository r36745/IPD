package com.rosemak.dogcentralv110.uifragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.adapters.SocialAdapter;
import com.rosemak.dogcentralv110.places.GooglePlace;
import com.rosemak.dogcentralv110.uiactivity.MainActivity;
import com.rosemak.dogcentralv110.uiactivity.PostActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stevierose on 12/11/15.
 */
public class SocialFragment extends Fragment {

    public static final String TAG = SocialFragment.class.getSimpleName();

    private ImageButton addPost;
    protected ProgressBar mProgressBar;
    public GooglePlace mGooglePlace;
    public ArrayList<GooglePlace> mArrayList;
    public String uri;
    public String username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            addPost.setVisibility(View.VISIBLE);
        }
        getLastPost();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar1);


        addPost = (ImageButton) getActivity().findViewById(R.id.addPostButton);
        addPost.setVisibility(View.GONE);
        /*addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });*/


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            addPost.setVisibility(View.VISIBLE);
            addPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    startActivity(intent);
                }
            });
        } else {

            new AlertDialog.Builder(getActivity())
                    .setTitle("To be social")
                    .setMessage("Please log in")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ParseLoginBuilder builder = new ParseLoginBuilder(getActivity());
                            startActivityForResult(builder.build(), 0);
                        }
                    }).show();
        }

    }

    protected void getLastPost(){
        mProgressBar.setVisibility(View.VISIBLE);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(PostFragment.POSTS);
        query.orderByDescending("createdAt");
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> results, ParseException e) {
                ListView listView = (ListView)getActivity().findViewById(R.id.socialList);
                mArrayList = new ArrayList<GooglePlace>();

                if (e == null) {
                    for (ParseObject result: results) {
                        mGooglePlace = new GooglePlace();
                        PostFragment postFragment = new PostFragment();

                        mGooglePlace.setmUserNotes(result.getString(postFragment.KEY_NOTES));
                        mGooglePlace.setPhoto(result.getString(postFragment.KEY_IMG));
                        mGooglePlace.setCurrentUser(result.getString(postFragment.KEY_NAME));
                        mGooglePlace.setmDay(result.getInt(postFragment.KEY_DAY));
                        mGooglePlace.setmMonth(result.getInt(postFragment.KEY_MONTH));
                        mGooglePlace.setmYear(result.getInt(postFragment.KEY_YEAR));
                        mGooglePlace.setTime(result.getString(postFragment.KEY_TIME));

                        mArrayList.add(mGooglePlace);

                    }

                    SocialAdapter socialAdapter = new SocialAdapter(getActivity(), mArrayList);
                    listView.setAdapter(socialAdapter);
                } else {
                    // check for the error
                }

            }
        });
    }


}
