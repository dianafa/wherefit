package com.diana.wherefit.impl;

import android.location.Location;
import android.util.SparseArray;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.api.SportActivityApi;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;
import com.diana.wherefit.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SportActivitiesServiceImpl implements SportActivitiesService {

    private static final int DAYS_TO_FETCH = 7;

    private static SportActivitiesService instance = new SportActivitiesServiceImpl();

    private Collection<SportActivityApi> apis;

    private SparseArray<Place> places;

    private Collection<SportActivity> activities;

    public SportActivitiesServiceImpl() {
        apis = new ArrayList<>();
        places = new SparseArray<>();
        activities = new ArrayList<>();
    }

    public static SportActivitiesService getInstance() {
        return instance;
    }

    @Override
    public List<SportActivity> getActivities(Location location, float dist, long from, long to, Collection<String> types) {
        List<SportActivity> nearbyActivities = new ArrayList<>();
        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();
        Calendar activityStart = Calendar.getInstance();
        Calendar activityEnd = Calendar.getInstance();
        calendarFrom.setTimeInMillis(from);
        calendarTo.setTimeInMillis(to);
        for (SportActivity activity : activities) {
            activityStart.setTimeInMillis(activity.getStartTime());
            activityEnd.setTimeInMillis(activity.getEndTime());
            if (TimeUtil.isBetween(activityStart, activityEnd, calendarFrom, calendarTo)
                    && isInPlaceNearby(activity, getPlaces(location, dist))
                    && isInType(activity, types)) {
                    nearbyActivities.add(activity);
            }
        }

        return nearbyActivities;
    }

    @Override
    public List<Place> getPlaces(Location location, float dist) {
        List<Place> nearbyPlaces = new ArrayList<>(places.size());
        for (int i = 0; i < places.size(); i++) {
            Place place = places.valueAt(i);
            if (location.distanceTo(place.getLocation()) <= dist) {
                nearbyPlaces.add(place);
            }
            nearbyPlaces.add(places.valueAt(i));
        }
        return nearbyPlaces;
    }

    @Override
    public List<String> getTypes(long from, long to) {
        Set<String> types = new HashSet<>();
        Calendar calendarFrom = Calendar.getInstance();
        Calendar calendarTo = Calendar.getInstance();
        Calendar activityStart = Calendar.getInstance();
        Calendar activityEnd = Calendar.getInstance();
        calendarFrom.setTimeInMillis(from);
        calendarTo.setTimeInMillis(to);
        for (SportActivity activity : activities) {
            activityStart.setTimeInMillis(activity.getStartTime());
            activityEnd.setTimeInMillis(activity.getEndTime());
            if (TimeUtil.isBetween(activityStart, activityEnd, calendarFrom, calendarTo)) {
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

        Calendar calendar = Calendar.getInstance();
        long from = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, DAYS_TO_FETCH);
        long to = calendar.getTimeInMillis();

        activities.addAll(activityApi.getActivities(from, to));
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

    private boolean isInType(SportActivity activity, Collection<String> types) {
        boolean result = true;
        if (types != null && !types.isEmpty()) {
            result = types.contains(activity.getType());
        }
        return result;
    }
}
