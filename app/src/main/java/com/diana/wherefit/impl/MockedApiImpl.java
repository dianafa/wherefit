package com.diana.wherefit.impl;

import android.content.Context;

import com.diana.wherefit.R;
import com.diana.wherefit.api.SportActivityApi;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.Places;
import com.diana.wherefit.pojo.SportActivities;
import com.diana.wherefit.pojo.SportActivity;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

public class MockedApiImpl implements SportActivityApi {

    private static final Gson GSON = new Gson();

    private final Places places;
    private final SportActivities activities;

    public MockedApiImpl(Context context) {
        Reader reader = new InputStreamReader(context.getResources().openRawResource(R.raw.activities));
        this.activities = GSON.fromJson(reader, SportActivities.class);
        reader = new InputStreamReader(context.getResources().openRawResource(R.raw.places));
        this.places = GSON.fromJson(reader, Places.class);
    }

    @Override
    public Collection<SportActivity> getActivities(long from, long to) {
        return activities.getActivities();
    }

    @Override
    public Collection<Place> getPlaces() {
        return places.getPlaces();
    }
}
