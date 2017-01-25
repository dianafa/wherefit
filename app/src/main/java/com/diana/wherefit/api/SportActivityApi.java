package com.diana.wherefit.api;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.Collection;

public interface SportActivityApi {

    Collection<SportActivity> getActivities();

    Collection<Place> getPlaces();
}
