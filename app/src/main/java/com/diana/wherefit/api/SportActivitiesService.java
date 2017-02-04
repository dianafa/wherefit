package com.diana.wherefit.api;

import android.location.Location;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.Collection;
import java.util.List;

public interface SportActivitiesService {

    List<SportActivity> getActivities(Location location, float dist, long from, long to);

    List<Place> getPlaces(Location location, float dist);

    List<String> getTypes(long from, long to);

    Place getPlace(int id);

    void addApi(SportActivityApi activityApi);
}
