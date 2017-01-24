package com.diana.wherefit.impl;

import android.location.Location;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.api.SportActivityApi;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SportActivitiesServiceImpl implements SportActivitiesService {

    private Collection<SportActivityApi> apis;

    public SportActivitiesServiceImpl() {
        apis = new ArrayList<>();
    }

    @Override
    public List<SportActivity> getActivities(Location location, float dist) {
        List<SportActivity> nearbyActivities = new ArrayList<>();
        for (SportActivityApi api : apis) {
            Location placeLocation = api.getPlace().getLocation();
            if (placeLocation != null && location != null) {
                float distanceTo = placeLocation.distanceTo(location);
                if (distanceTo <= dist) {
                    nearbyActivities.addAll(api.getActivities());
                }
            }
            api.getPlace().getLocation().distanceTo(location);
        }
        return nearbyActivities;
    }

    @Override
    public List<Place> getPlaces(Location location, float dist) {
        List<Place> nearbyPlaces = new ArrayList<>();
        for (SportActivityApi api : apis) {
            Place place = api.getPlace();
            if (location != null && place != null) {
                if (location.distanceTo(place.getLocation()) <= dist) {
                    nearbyPlaces.add(place);
                }
            }
        }
        return nearbyPlaces;
    }

    @Override
    public void addApi(SportActivityApi activityApi) {
        apis.add(activityApi);
    }
}
