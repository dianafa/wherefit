package com.diana.wherefit;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;
import com.diana.wherefit.pojo.SportActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ClassListActivity extends AppCompatActivity {
    private static final float DEFAULT_DISTANCE = 10_000f; // 10km
    ArrayList<String> types = null;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        getLocationPermissions();
        getLocation();

        Bundle b = getIntent().getExtras();
        types = b.getStringArrayList("types");

        initSportActivities(SportActivitiesServiceImpl.getInstance(), location);
    }

    private void initSportActivities(SportActivitiesService service, Location location) {
        List<SportActivity> activities;

        if (service != null) {
            Calendar now = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            to.add(Calendar.DAY_OF_MONTH, 3); // 3 days ahead
            activities = types == null ?
                service.getActivities(location, DEFAULT_DISTANCE, now.getTimeInMillis(), to.getTimeInMillis()) :
                service.getActivitiesFromType(location, DEFAULT_DISTANCE, now.getTimeInMillis(), to.getTimeInMillis(), types);

            Collections.sort(activities, new SportActivityTimeComparator());
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(new SportActivityArrayAdapter(this, activities, service));
            listView.setOnItemClickListener(new SportActivityItemClickListener(this, listView, service));
        }
    }

    private static Location getDefaultLocation() {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(52.4039071);
        location.setLongitude(16.9500184);
        return location;
    }

    private void getLocation() {
        Location loc = null;
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        location = loc == null ? getDefaultLocation() : loc;
    }

    private void getLocationPermissions() {
        PermissionRequest.permissionCheck(this, android.Manifest.permission.ACCESS_COARSE_LOCATION, 0);
        PermissionRequest.permissionCheck(this, android.Manifest.permission.ACCESS_FINE_LOCATION, 1);
    }
}
