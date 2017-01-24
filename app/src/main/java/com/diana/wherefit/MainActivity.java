package com.diana.wherefit;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAll(View view) {
        getLocationPermissions();
        Intent intent = new Intent(this, ClassListActivity.class);
        String message = "dianka";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void getLocationPermissions() {
        PermissionRequest.permissionCheck(this, Manifest.permission.ACCESS_COARSE_LOCATION, 0);
        PermissionRequest.permissionCheck(this, Manifest.permission.ACCESS_FINE_LOCATION, 1);
    }
}
