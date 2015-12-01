package com.rosemak.dogcentralv106.places;

import java.io.Serializable;

/**
 * Created by stevierose on 11/23/15.
 */
public class GooglePlace implements Serializable {
    private String name;
    private String category;
    private Float rating;
    private String open;
    private String photo;
    public double mLat;


    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public double mLng;
    public String vicinity;


    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public GooglePlace() {
        this.name = "";

        this.open = "";
        this.setCategory("");
    }

    public void setCategory(String category) {
        this.category = category;

    }


    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }



    public String setOpen(String open) {
        this.open = open;
        return open;
    }

    public String getOpen() {
        return open;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLng() {
        return mLng;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;
    }
}

