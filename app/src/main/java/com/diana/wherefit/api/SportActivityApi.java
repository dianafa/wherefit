package com.diana.wherefit.api;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.ArrayList;
import java.util.Collection;

public interface SportActivityApi {

    Collection<SportActivity> getActivities(long from, long to);

    Collection<Place> getPlaces();
}
