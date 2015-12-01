package com.rosemak.dogcentralv106.places;

import java.io.Serializable;

/**
 * Created by stevierose on 11/29/15.
 */
public class FourSquarePlace implements Serializable {

    private String mName;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mHereNow;
    private double mLat;
    private double mLng;
    private String mPhoneNum;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmHereNow() {
        return mHereNow;
    }

    public void setmHereNow(String mHereNow) {
        this.mHereNow = mHereNow;
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

    public String getmPhoneNum() {
        return mPhoneNum;
    }

    public void setmPhoneNum(String mPhoneNum) {
        this.mPhoneNum = mPhoneNum;
    }

    public FourSquarePlace(){

    }
}
