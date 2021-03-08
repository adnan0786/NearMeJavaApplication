package com.example.nearmedemo.Model.GooglePlaceModel;

import com.example.nearmedemo.GooglePlaceModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleResponseModel {

    @SerializedName("results")
    @Expose
    private List<GooglePlaceModel> googlePlaceModelList;

    public List<GooglePlaceModel> getGooglePlaceModelList() {
        return googlePlaceModelList;
    }

    public void setGooglePlaceModelList(List<GooglePlaceModel> googlePlaceModelList) {
        this.googlePlaceModelList = googlePlaceModelList;
    }
}
