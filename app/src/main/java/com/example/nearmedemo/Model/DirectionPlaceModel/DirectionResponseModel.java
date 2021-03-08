package com.example.nearmedemo.Model.DirectionPlaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionResponseModel {

    @SerializedName("routes")
    @Expose
    List<DirectionRouteModel> directionRouteModels;


    public List<DirectionRouteModel> getDirectionRouteModels() {
        return directionRouteModels;
    }

    public void setDirectionRouteModels(List<DirectionRouteModel> directionRouteModels) {
        this.directionRouteModels = directionRouteModels;
    }
}
