package com.diana.wherefit;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.diana.wherefit.api.FabrykaFormyApi;
import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.SiteContentDownloader;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {
    private SportActivitiesService activitiesService;
    private boolean apisLoaded = false;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocation();

        activitiesService = SportActivitiesServiceImpl.getInstance();

        getLoaderManager().initLoader(0, savedInstanceState,
                new LoaderManager.LoaderCallbacks<FabrykaFormyApi>() {
                    @Override public Loader<FabrykaFormyApi> onCreateLoader(int id, Bundle args) {
                        return new SiteContentDownloader(MainActivity.this);
                    }

                    @Override public void onLoadFinished(Loader<FabrykaFormyApi> loader, FabrykaFormyApi api) {
                        activitiesService.addApi(api);
                        apisLoaded = true;
                    }

                    @Override public void onLoaderReset(Loader<FabrykaFormyApi> loader) {
                        activitiesService.addApi(new FabrykaFormyApi());
                        //activitiesService.addApi(new MockedApiImpl(this));
                        apisLoaded = true;
                    }
                }
        ).forceLoad();
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

    public void showAll(View view) {
        getLocationPermissions();
        Intent intent = new Intent(this, ClassListActivity.class);
        if (apisLoaded) {
            intent.putExtra("LOCATION", location);
            startActivity(intent);
        }
    }

    public void showTypeSelection(View view) {
        getLocationPermissions();
        Intent intent = new Intent(this, TypeListActivity.class);
        if (apisLoaded) {
            startActivity(intent);
        }
    }

    public SportActivitiesService getAtivitiesService() {
        return activitiesService;
    }

    private void getLocationPermissions() {
        PermissionRequest.permissionCheck(this, Manifest.permission.ACCESS_COARSE_LOCATION, 0);
        PermissionRequest.permissionCheck(this, Manifest.permission.ACCESS_FINE_LOCATION, 1);
    }
}
