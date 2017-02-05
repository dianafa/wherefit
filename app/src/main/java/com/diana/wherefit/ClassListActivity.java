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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ClassListActivity extends AppCompatActivity {

    private static final float DEFAULT_DISTANCE = 10_000f; // 10km

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        initSportActivities(SportActivitiesServiceImpl.getInstance(), getLocation(), loadTypes());
    }

    private Collection<String> loadTypes() {
        Bundle b = getIntent().getExtras();
        Collection<String> types = null;
        if (b != null) {
            types = b.getStringArrayList("types");
        }
        return types;
    }

    private void initSportActivities(SportActivitiesService service, Location location, Collection<String> types) {
        List<SportActivity> activities;

        if (service != null) {
            Calendar now = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            to.add(Calendar.DAY_OF_MONTH, 3); // 3 days ahead
            activities = service.getActivities(location, DEFAULT_DISTANCE, now.getTimeInMillis(), to.getTimeInMillis(), types);

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

    private Location getLocation() {
        Location loc = null;
        getLocationPermissions();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        return loc == null ? getDefaultLocation() : loc;
    }

    private void getLocationPermissions() {
        PermissionRequest.permissionCheck(this, android.Manifest.permission.ACCESS_COARSE_LOCATION, 0);
        PermissionRequest.permissionCheck(this, android.Manifest.permission.ACCESS_FINE_LOCATION, 1);
    }
}
