package com.diana.wherefit.impl;

import android.location.Location;
import android.util.SparseArray;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.api.SportActivityApi;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SportActivitiesServiceImpl implements SportActivitiesService {

    private static SportActivitiesService instance = new SportActivitiesServiceImpl();

    private Collection<SportActivityApi> apis;

    private SparseArray<Place> places;

    public SportActivitiesServiceImpl() {
        apis = new ArrayList<>();
        places = new SparseArray<>();
    }

    public static SportActivitiesService getInstance() {
        return instance;
    }

    @Override
    public List<SportActivity> getActivities(Location location, float dist, long from, long to) {
        List<SportActivity> nearbyActivities = new ArrayList<>();
        List<Place> places = getPlaces(location, dist);
        for (SportActivityApi api : apis) {
            for (SportActivity activity : api.getActivities(from, to)) {
                if (isInPlaceNearby(activity, places)) {
                    nearbyActivities.add(activity);
                }
            }
        }
        return nearbyActivities;
    }

    @Override
    public List<SportActivity> getActivitiesFromType(Location location, float dist, long from, long to, ArrayList<String> types) {
        List<SportActivity> nearbyActivities = new ArrayList<>();
        List<Place> places = getPlaces(location, dist);
        for (SportActivityApi api : apis) {
            for (SportActivity activity : api.getActivitiesFromType(from, to, types)) {
                if (isInPlaceNearby(activity, places)) {
                    nearbyActivities.add(activity);
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
    public List<String> getTypes(long from, long to) {
        Set<String> types = new HashSet<>();
        for (SportActivityApi api : apis) {
            for (SportActivity activity : api.getActivities(from, to)) {
                String type = activity.getType();
                if (type != null) {
                    types.add(type);
                }
            }
        }

        ArrayList<String> typesList = new ArrayList<>(types);
        Collections.sort(typesList);

        return typesList;
    }

    @Override
    public Place getPlace(int id) {
        return places.get(id);
    }

    @Override
    public void addApi(SportActivityApi activityApi) {
        apis.add(activityApi);
        for (Place place : activityApi.getPlaces()) {
            places.put(place.getId(), place);
        }
    }

    private boolean isInPlaceNearby(SportActivity activity, Collection<Place> nearbyPlaces) {
        boolean result = false;
        for (Place place : nearbyPlaces) {
            if (place.getId() == activity.getPlaceId()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
