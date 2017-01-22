package com.diana.wherefit;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SportActivitiesTest {

    private Gson gson;
    private Reader reader;

    @Before
    public void setUp() throws Exception {
        this.gson = new Gson();
        Context appContext = InstrumentationRegistry.getTargetContext();
        InputStream json = appContext.getResources().openRawResource(R.raw.activities);
        this.reader = new InputStreamReader(json);
    }

    @Test
    public void readActivitiesList() throws Exception {
        assertNotNull(reader);

        SportActivities sportActivities = gson.fromJson(reader, SportActivities.class);

        assertNotNull(sportActivities);
        assertNotNull(sportActivities.getActivities());
        assertFalse(sportActivities.getActivities().isEmpty());
    }
}