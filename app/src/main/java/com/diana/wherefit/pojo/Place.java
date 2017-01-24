package com.diana.wherefit.pojo;

import android.location.Location;
import android.location.LocationManager;

import java.io.Serializable;
import java.util.Locale;

public class Place implements Serializable {

    private int id;

    private String name;

    private double latitude;

    private double longitude;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location getLocation() {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(getLatitude());
        location.setLongitude(getLongitude());
        return location;
    }
}
