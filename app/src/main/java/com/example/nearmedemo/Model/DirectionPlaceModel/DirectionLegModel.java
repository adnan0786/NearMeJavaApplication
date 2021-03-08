package com.example.nearmedemo.Model.DirectionPlaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionLegModel {


    @SerializedName("distance")
    @Expose
    private DirectionDistanceModel distance;
    @SerializedName("duration")
    @Expose
    private DirectionDurationModel duration;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("end_location")
    @Expose
    private EndLocationModel endLocation;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_location")
    @Expose
    private StartLocationModel startLocation;
    @SerializedName("steps")
    @Expose
    private List<DirectionStepModel> steps = null;


    public DirectionDistanceModel getDistance() {
        return distance;
    }

    public void setDistance(DirectionDistanceModel distance) {
        this.distance = distance;
    }

    public DirectionDurationModel getDuration() {
        return duration;
    }

    public void setDuration(DirectionDurationModel duration) {
        this.duration = duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public EndLocationModel getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocationModel endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public StartLocationModel getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocationModel startLocation) {
        this.startLocation = startLocation;
    }

    public List<DirectionStepModel> getSteps() {
        return steps;
    }

    public void setSteps(List<DirectionStepModel> steps) {
        this.steps = steps;
    }


}
