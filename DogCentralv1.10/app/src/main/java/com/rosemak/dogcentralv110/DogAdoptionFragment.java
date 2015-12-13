package com.rosemak.dogcentralv110;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.rosemak.dogcentralv106.R;
import com.rosemak.dogcentralv106.UIHelper;
import com.rosemak.dogcentralv106.adapters.DogAdoptionAdapter;
import com.rosemak.dogcentralv106.places.GooglePlace;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by stevierose on 12/5/15.
 */
public class DogAdoptionFragment extends Fragment  {
    public static final String TAG = DogAdoptionFragment.class.getSimpleName();

    public double mLat;
    public double mLng;
    private LocationManager mLocation;
    private static final int REQUEST_ENABLE_GPS = 0x02001;
    private GooglePlace gPlace;
    private JSONObject gPlacesData;
    private JSONArray jsonArray;
    private JSONObject placesObject;
    private JSONObject dogObject;
    protected double gLat = 38.94048168;
    protected double gLng = -76.82931342;
    protected ProgressBar mProgressBar;
    private OnAdoptionClick mListener;
    private URL queryURL;
    private ImageButton refreshButton;


    public interface OnAdoptionClick{
        public void dogList(GooglePlace dog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnAdoptionClick){
            mListener = (OnAdoptionClick) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dog_adoption, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocation = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UIHelper helper = new UIHelper();
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
        refreshButton = (ImageButton) getActivity().findViewById(R.id.refreshButton);
        //refreshButton.setVisibility(View.GONE);


        if (helper.isNetworkAvailable(getActivity())){
            try {
                mProgressBar.setVisibility(View.VISIBLE);

                //enableGps();
                String dogData = "http://api.petfinder.com/pet.getRandom?format=json&animal=dog&breed=&output=basic&key=31385130ba6f55a45c2a06477f7d687e";


                queryURL = new URL(dogData);
                new GetPets().execute(queryURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            //Add Alert

            new AlertDialog.Builder(getActivity())
                    .setTitle("Network Unavailable")
                    .setMessage("Please check system settings")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }
                    }).show();

            Log.d(TAG, "No Network");
        }

        //refreshButton = (ImageButton) getActivity().findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetPets().execute(queryURL);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UIHelper helper = new UIHelper();
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);

        if (helper.isNetworkAvailable(getActivity())){
            try {
                mProgressBar.setVisibility(View.VISIBLE);

                //enableGps();
                String dogData = "http://api.petfinder.com/pet.getRandom?format=json&animal=dog&breed=&output=basic&key=31385130ba6f55a45c2a06477f7d687e";


                URL queryURL = new URL(dogData);
                new GetPets().execute(queryURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            //Add Alert
            new AlertDialog.Builder(getActivity())
                    .setTitle("Network Unavailable")
                    .setMessage("Please check system settings")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(settingsIntent, REQUEST_ENABLE_GPS);
                        }
                    })
                    .show();
            Log.d(TAG, "No Network");
        }
    }

    private class GetPets extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {

            String jsonString = "";

            for (URL queryURL : urls) {
                try {
                    URLConnection conn = queryURL.openConnection();
                    jsonString = IOUtils.toString(conn.getInputStream());
                    break;
                } catch (IOException e) {
                    Log.e(TAG, "Could not establish URLConnection to " + queryURL.toString());
                }
            }
            JSONObject placesData;

            try {
                placesData = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
                placesData = null;
            }


            return placesData;
        }

        @Override
        protected void onPostExecute(JSONObject result) {


            //final ArrayList<GooglePlace> arrayList = new ArrayList<GooglePlace>();

            try {
                gPlacesData = result;

                JSONObject jsonObject = gPlacesData.getJSONObject("petfinder");

                //jsonArray = gPlacesData.getJSONArray("results");

                for (int i = 0; i < jsonObject.length(); i++) {
                    final ArrayList<GooglePlace> arrayList = new ArrayList<GooglePlace>();
                    final GooglePlace gPlace = new GooglePlace();

                    if (jsonObject.getJSONObject("pet").has("contact")){
                        JSONObject contactObj = jsonObject.getJSONObject("pet").getJSONObject("contact");
                        JSONObject emailObj = contactObj.getJSONObject("email");
                        if (emailObj.getString("$t") != null){
                            gPlace.setEmail(emailObj.getString("$t"));
                        } else {
                            gPlace.setEmail("no email");
                        }

                    }

                    if (jsonObject.getJSONObject("pet").has("age")){
                        JSONObject ageObj = jsonObject.getJSONObject("pet").getJSONObject("age");
                        String age = ageObj.getString("$t");
                        if (age != null) {
                            gPlace.setAge(ageObj.getString("$t"));
                        }  else {
                            gPlace.setAge("no age");
                        }

                    }

                    if (jsonObject.getJSONObject("pet").has("size")){
                        JSONObject sizeObj = jsonObject.getJSONObject("pet").getJSONObject("size");
                        String size = sizeObj.getString("$t");
                        if (size != null) {
                            gPlace.setSize(sizeObj.getString("$t"));
                        } else {
                            gPlace.setSize(sizeObj.getString("no size"));
                        }

                    }

                    if (jsonObject.getJSONObject("pet").has("media")){
                        JSONArray photoObj = jsonObject.getJSONObject("pet").getJSONObject("media").getJSONObject("photos").getJSONArray("photo");
                        String newPhoto = photoObj.getJSONObject(i).getString("$t");
                        if (newPhoto != null) {
                            gPlace.setAdoptionPhoto(photoObj.getJSONObject(i).getString("$t"));
                        } else {
                            gPlace.setAdoptionPhoto("no media");
                        }

                    }

                    if (jsonObject.getJSONObject("pet").has("breeds")){
                        JSONObject breedObj = jsonObject.getJSONObject("pet").getJSONObject("breeds");
                        String breed = breedObj.getJSONObject("breed").getString("$t");
                        if (breed != null) {
                            gPlace.setBreed(breedObj.getJSONObject("breed").getString("$t"));
                        } else{
                            gPlace.setBreed("no breed");
                        }

                    }

                    if (jsonObject.getJSONObject("pet").has("description")) {
                        JSONObject descriptionObj = jsonObject.getJSONObject("pet").getJSONObject("description");
                        String description = descriptionObj.getString("$t");
                        if (description != null) {
                            gPlace.setAdoptedDogDescription(descriptionObj.getString("$t"));
                        } else {
                            gPlace.setAdoptedDogDescription("no description");
                        }


                    }





                    mProgressBar.setVisibility(View.INVISIBLE);
                    arrayList.add(gPlace);
                    DogAdoptionAdapter arrayAdapter = new DogAdoptionAdapter(getActivity(), arrayList);
                    ListView placesList = (ListView) getActivity().findViewById(R.id.list);
                    placesList.setAdapter(arrayAdapter);

                    placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            mListener.dogList(arrayList.get(position));
                        }
                    });

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
