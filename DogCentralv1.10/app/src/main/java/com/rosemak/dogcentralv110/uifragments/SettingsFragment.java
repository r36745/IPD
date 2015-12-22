package com.rosemak.dogcentralv110.uifragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.rosemak.dogcentralv110.R;
import com.rosemak.dogcentralv110.uiactivity.SocialActivity;

/**
 * Created by stevierose on 12/19/15.
 */
public class SettingsFragment extends Fragment {

    public static final String TAG = SettingsFragment.class.getSimpleName();
    public String KEY_NNAME = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button updateButton = (Button)getActivity().findViewById(R.id.usernameUpdateButton);
        final EditText updateUsername = (EditText) getActivity().findViewById(R.id.updatedUsername);
        final EditText updatedPassword = (EditText) getActivity().findViewById(R.id.updatedPassword);
        final EditText updatedUseremail = (EditText) getActivity().findViewById(R.id.updatedUseremail);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser currentUser = ParseUser.getCurrentUser();
                String newUsername = updateUsername.getText().toString();
                String newPassword = updatedPassword.getText().toString();
                String newUserEmail = updatedUseremail.getText().toString();



                currentUser.setUsername(newUsername);
                currentUser.setPassword(newPassword);
                currentUser.setEmail(newUserEmail);
                currentUser.saveInBackground();

                updateUsername.setText("");
                updatedPassword.setText("");
                updatedUseremail.setText("");

                new AlertDialog.Builder(getActivity())
                        .setTitle("User has been updated")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ParseUser currentUser = ParseUser.getCurrentUser();
                                currentUser.logOut();
                                Toast.makeText(getActivity(), "Please Log back in", Toast.LENGTH_LONG)
                                        .show();


                                Intent socialIntent = new Intent(getActivity(), SocialActivity.class);
                                startActivity(socialIntent);

                            }
                        }).show();

            }
        });

    }
}
