package com.example.nearmedemo.Adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;

import com.example.nearmedemo.databinding.InfoWindowLayoutBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.SphericalUtil;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private InfoWindowLayoutBinding binding;
    private Location location;
    private Context context;

    public InfoWindowAdapter(Location location, Context context) {

        this.location = location;
        this.context = context;

        binding = InfoWindowLayoutBinding.inflate(LayoutInflater.from(context), null, false);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        binding.txtLocationName.setText(marker.getTitle());

        double distance = SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()),
                marker.getPosition());

        if (distance > 1000) {
            double kilometers = distance / 1000;
            binding.txtLocationDistance.setText(distance + " KM");
        } else {
            binding.txtLocationDistance.setText(distance + " Meters");

        }

        float speed = location.getSpeed();

        if (speed > 0) {
            double time = distance / speed;
            binding.txtLocationTime.setText(time + " sec");
        } else {
            binding.txtLocationTime.setText("N/A");
        }
        return binding.getRoot();
    }

    @Override
    public View getInfoContents(Marker marker) {

        binding.txtLocationName.setText(marker.getTitle());

        double distance = SphericalUtil.computeDistanceBetween(new LatLng(location.getLatitude(), location.getLongitude()),
                marker.getPosition());

        if (distance > 1000) {
            double kilometers = distance / 1000;
            binding.txtLocationDistance.setText(distance + " KM");
        } else {
            binding.txtLocationDistance.setText(distance + " Meters");

        }

        float speed = location.getSpeed();

        if (speed > 0) {
            double time = distance / speed;
            binding.txtLocationTime.setText(time + " sec");
        } else {
            binding.txtLocationTime.setText("N/A");
        }
        return binding.getRoot();
    }
}
