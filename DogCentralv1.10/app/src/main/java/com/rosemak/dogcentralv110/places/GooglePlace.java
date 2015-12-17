package com.rosemak.dogcentralv110.places;

import android.net.Uri;

import com.parse.ParseUser;

import java.io.Serializable;

/**
 * Created by stevierose on 11/23/15.
 */
public class GooglePlace implements Serializable {
    private String name;
    private String category;

    private String open;
    private String photo;
    public double mLat;
    public double mLng;
    public String vicinity;
    public String mImage;
    public String mUserNotes;
    public String mUserPost;
    public Uri mImg;
    public String email;
    public String phone;
    public String adoptionPhoto;
    public String age;
    public String size;
    public String breed;
    public String adoptedDogName;
    public String adoptedDogDescription;
    public Float rating;
    public Float socialRating;
    public String postRating;
    public ParseUser currentUser;

    public ParseUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ParseUser currentUser) {
        this.currentUser = currentUser;
    }

    public String getPostRating() {
        return postRating;
    }

    public void setPostRating(String postRating) {
        this.postRating = postRating;
    }

    public Float getSocialRating() {
        return socialRating;
    }

    public void setSocialRating(Float socialRating) {
        this.socialRating = socialRating;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public String setPhoto(String photo) {
        this.photo = photo;
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdoptionPhoto() {
        return adoptionPhoto;
    }

    public void setAdoptionPhoto(String adoptionPhoto) {
        this.adoptionPhoto = adoptionPhoto;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAdoptedDogName() {
        return adoptedDogName;
    }

    public void setAdoptedDogName(String adoptedDogName) {
        this.adoptedDogName = adoptedDogName;
    }

    public String getAdoptedDogDescription() {
        return adoptedDogDescription;
    }

    public void setAdoptedDogDescription(String adoptedDogDescription) {
        this.adoptedDogDescription = adoptedDogDescription;
    }

    public Uri getmImg() {
        return mImg;
    }

    public void setmImg(Uri mImg) {
        this.mImg = mImg;
    }

    public String getmUserPost() {
        return mUserPost;
    }

    public void setmUserPost(String mUserPost) {
        this.mUserPost = mUserPost;
    }

    public String getmUserNotes() {
        return mUserNotes;
    }

    public void setmUserNotes(String mUserNotes) {
        this.mUserNotes = mUserNotes;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }


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

