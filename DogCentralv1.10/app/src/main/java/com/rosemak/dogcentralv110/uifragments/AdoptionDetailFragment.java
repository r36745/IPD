package com.rosemak.dogcentralv110.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.rosemak.dogcentralv110.R;

/**
 * Created by stevierose on 12/7/15.
 */
public class AdoptionDetailFragment extends Fragment {
    public static final String TAG = AdoptionDetailFragment.class.getSimpleName();
    private OnDogAdoptionClick mListener;


    public interface OnDogAdoptionClick{
        public String breedName();
        public String breedDescription();
        public String breedImg();
        public String email();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnDogAdoptionClick) {
            mListener = (OnDogAdoptionClick) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adoption_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView breed = (TextView) getActivity().findViewById(R.id.adoptableBreed);
        TextView description = (TextView) getActivity().findViewById(R.id.adoptableDescription);
        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.adoptableImage);
        TextView email = (TextView) getActivity().findViewById(R.id.email);

        breed.setText(mListener.breedName());
        description.setText(mListener.breedDescription());
        email.setText(mListener.email());

        ImageLoader imageLoader = ImageLoader.getInstance();
        String imageUri = mListener.breedImg();

        imageLoader.displayImage(imageUri, imageView);
        imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // load Bitmap
                imageView.setImageBitmap(loadedImage);
            }
        });





    }
}
