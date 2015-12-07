package com.rosemak.dogcentralv106.uifragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.rosemak.dogcentralv106.R;

import java.util.List;

/**
 * Created by stevierose on 12/4/15.
 */
public class ActiveUsersFragment extends ListFragment {
    public static final String TAG = ActiveUsersFragment.class.getSimpleName();
    protected ProgressBar mProgressBar;
    protected ParseObject[] mUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active_users, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
         getAllUsers();
    }

    private void getAllUsers() {
        mProgressBar.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(PostFragment.POSTS);
        query.orderByDescending("createdAt");
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    objects = removeCurrentUser(objects);
                    mUsers = objects.toArray(new ParseObject[0]);

                    ParseRelation<ParseObject> userRelations = ParseUser.getCurrentUser().getRelation("UserRelation");
                    userRelations.getQuery().findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {

                            }
                        }
                    });
                }
            }
        });



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private List<ParseObject> removeCurrentUser(List<ParseObject> objects) {
        ParseObject userToRemove = null;

        for (ParseObject user : objects) {
            if (user.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                userToRemove = user;
            }
        }

        if (userToRemove != null) {
            objects.remove(userToRemove);
        }

        return objects;
    }

    ParseRelation userRelations = ParseUser.getCurrentUser().getRelation("UserRelation");
}
