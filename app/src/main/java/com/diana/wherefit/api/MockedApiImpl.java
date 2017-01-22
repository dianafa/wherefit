package com.diana.wherefit.api;

import android.content.Context;
import android.location.Location;

import com.diana.wherefit.R;
import com.diana.wherefit.pojo.Places;
import com.diana.wherefit.pojo.SportActivities;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;

public class MockedApiImpl implements Api {

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
    public SportActivities getActivities(Location location) {
        return activities;
    }

    @Override
    public Places getPlaces(Location location) {
        return places;
    }
}
