package com.diana.wherefit.impl;

import android.location.Location;
import android.util.SparseArray;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.api.SportActivityApi;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

public class SportActivitiesServiceImpl implements SportActivitiesService {

    private Collection<SportActivityApi> apis;

    private SparseArray<Place> places;

    public SportActivitiesServiceImpl() {
        apis = new ArrayList<>();
        places = new SparseArray<>();
        ServiceLoader<SportActivityApi> load = ServiceLoader.load(SportActivityApi.class);
        for (SportActivityApi api : load) {
            apis.add(api);
        }
    }

    @Override
    public List<SportActivity> getActivities(Location location, float dist) {
        List<SportActivity> nearbyActivities = new ArrayList<>();
        for (SportActivityApi api : apis) {
            for (Place place: api.getPlaces()) {
                Location placeLocation = place.getLocation();
                if (placeLocation != null && location != null) {
                    float distanceTo = placeLocation.distanceTo(location);
                    if (distanceTo <= dist) {
                        nearbyActivities.addAll(api.getActivities());
                    }
                }
            }
        }
        return nearbyActivities;
    }

    @Override
    public List<Place> getPlaces(Location location, float dist) {
        List<Place> nearbyPlaces = new ArrayList<>();
        for (SportActivityApi api : apis) {
            Collection<Place> places = api.getPlaces();
            for (Place place : places) {
                if (location != null && place != null) {
                    if (location.distanceTo(place.getLocation()) <= dist) {
                        nearbyPlaces.add(place);
                    }
                }
            }
        }
        return nearbyPlaces;
    }

    @Override
    public Place getPlace(int id) {
        return places.get(id);
    }

    @Override
    public void addApi(SportActivityApi activityApi) {
        apis.add(activityApi);
        for (Place place: activityApi.getPlaces()) {
            places.put(place.getId(), place);
        }
    }
}
