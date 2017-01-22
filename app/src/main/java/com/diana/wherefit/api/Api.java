package com.diana.wherefit.api;

import android.location.Location;

import com.diana.wherefit.pojo.Places;
import com.diana.wherefit.pojo.SportActivities;

public interface Api {

    SportActivities getActivities(Location location);

    Places getPlaces(Location location);
}
