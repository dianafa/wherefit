package com.diana.wherefit;

import java.io.Serializable;

public class Place implements Serializable {

    private int id;

    private String name;

    private String latitude;

    private String longitude;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
