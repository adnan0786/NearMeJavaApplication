package com.example.nearmedemo;

import com.example.nearmedemo.Model.GooglePlaceModel.GeometryModel;
import com.example.nearmedemo.Model.GooglePlaceModel.PhotoModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GooglePlaceModel {

    @SerializedName("business_status")
    @Expose
    private String businessStatus;

    @SerializedName("geometry")
    @Expose
    private GeometryModel geometry;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("obfuscated_type")
    @Expose
    private List<Object> obfuscatedType = null;

    @SerializedName("photos")
    @Expose
    private List<PhotoModel> photos = null;

    @SerializedName("place_id")
    @Expose
    private String placeId;


    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("reference")
    @Expose
    private String reference;

    @SerializedName("scope")
    @Expose
    private String scope;

    @SerializedName("types")
    @Expose
    private List<String> types = null;

    @SerializedName("user_ratings_total")
    @Expose
    private Integer userRatingsTotal;

    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    @Expose(serialize = false, deserialize = false)
    private boolean isSaved;

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryModel geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getObfuscatedType() {
        return obfuscatedType;
    }

    public void setObfuscatedType(List<Object> obfuscatedType) {
        this.obfuscatedType = obfuscatedType;
    }


    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Integer getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(Integer userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
