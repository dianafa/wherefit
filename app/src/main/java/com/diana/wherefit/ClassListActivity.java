package com.diana.wherefit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.diana.wherefit.api.Api;
import com.diana.wherefit.api.MockedApiImpl;
import com.diana.wherefit.pojo.Places;
import com.diana.wherefit.pojo.SportActivities;

import java.util.Collections;

public class ClassListActivity extends AppCompatActivity {

    private Places places;

    private SportActivities activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Api api = new MockedApiImpl(this);
        initSportActivities(api, getCurrentLocation());

        Collections.sort(activities.getActivities());

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new SportActivityArrayAdapter(this, activities, places));
        listView.setOnItemClickListener(new SportActivityItemClickListener(this, listView));

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_class_list);
        layout.addView(textView);
    }

    private Location getCurrentLocation() {
        Location location = null;
        PermissionRequest.permissionCheck(this, Manifest.permission.ACCESS_COARSE_LOCATION, 0);
        PermissionRequest.permissionCheck(this, Manifest.permission.ACCESS_FINE_LOCATION, 1);
        boolean permissionsGranted =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (permissionsGranted) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        return location;
    }

    private void initSportActivities(Api api, Location location) {
        this.places = api.getPlaces(location);
        this.activities = api.getActivities(location);
    }
}
