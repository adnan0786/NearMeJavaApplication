package com.example.nearmedemo.Model.DirectionPlaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DirectionStepModel {

    @SerializedName("distance")
    @Expose
    private DirectionDistanceModel distance;
    @SerializedName("duration")
    @Expose
    private DirectionDurationModel duration;
    @SerializedName("end_location")
    @Expose
    private EndLocationModel endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private DirectionPolylineModel polyline;
    @SerializedName("start_location")
    @Expose
    private StartLocationModel startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;

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

    public EndLocationModel getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocationModel endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public DirectionPolylineModel getPolyline() {
        return polyline;
    }

    public void setPolyline(DirectionPolylineModel polyline) {
        this.polyline = polyline;
    }

    public StartLocationModel getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocationModel startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }
}
