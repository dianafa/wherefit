package com.diana.wherefit;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.diana.wherefit.api.FabrykaFormyApi;
import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.DownloadSiteContent;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;
import com.diana.wherefit.pojo.SportActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ClassListActivity extends AppCompatActivity {

    private static final float DEFAULT_DISTANCE = 10_000f; //in meters (10km)
    private Location location;
    private SportActivitiesService activitiesService;

    private static Location getDefaultLocation() {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(52.4039071);
        location.setLongitude(16.9500184);
        return location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitiesService = new SportActivitiesServiceImpl();

        getLoaderManager().initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<FabrykaFormyApi>() {
                    @Override public Loader<FabrykaFormyApi> onCreateLoader(int id, Bundle args) {
                        return new DownloadSiteContent(ClassListActivity.this);
                    }

                    @Override public void onLoadFinished(Loader<FabrykaFormyApi> loader, FabrykaFormyApi data) {
                        activitiesService.addApi(data);
                        initView();
                    }

                    @Override public void onLoaderReset(Loader<FabrykaFormyApi> loader) {
                        activitiesService.addApi(new FabrykaFormyApi());
                        initView();
                        //activitiesService.addApi(new MockedApiImpl(this));
                    }
                }
        ).forceLoad();




    }

    private void initView() {
        setContentView(R.layout.activity_class_list);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_class_list);
        layout.addView(textView);

        initLocation();
    }

    private void initLocation() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initSportActivities(activitiesService, location);
        }
    }

    private void initSportActivities(SportActivitiesService service, Location loc) {
        List<SportActivity> activities;
        Location location = loc == null ? getDefaultLocation() : loc;

        if (service != null) {
            Calendar now = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            to.add(Calendar.DAY_OF_MONTH, 3); // 3 days ahead
            activities = service.getActivities(location, DEFAULT_DISTANCE, now.getTimeInMillis(), to.getTimeInMillis());
            Collections.sort(activities, new SportActivityTimeComparator());
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(new SportActivityArrayAdapter(this, activities, service));
            listView.setOnItemClickListener(new SportActivityItemClickListener(this, listView, service));
        }
    }
}
