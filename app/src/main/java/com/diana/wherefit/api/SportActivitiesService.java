package com.diana.wherefit.api;

import android.location.Location;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.List;

public interface SportActivitiesService {

    List<SportActivity> getActivities(Location location, float dist);

    List<Place> getPlaces(Location location, float dist);

    Place getPlace(int id);

    void addApi(SportActivityApi activityApi);
}
