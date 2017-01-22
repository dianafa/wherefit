package com.diana.wherefit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public final class PermissionRequest {

    public static void permissionCheck(Activity activity, String permission, int requestCode) {
        int permissionCheck = ContextCompat.checkSelfPermission(
                activity, permission);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showExplanation(activity, "Permission Needed", "Rationale", permission, requestCode);
            } else {
                requestPermission(activity, permission, requestCode);
            }
        } else {
            Toast.makeText(activity, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }

    }

    private static void showExplanation(final Activity activity, String title, String message, final String permission,
                                        final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(activity, permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private static void requestPermission(Activity activity, String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permissionName}, permissionRequestCode);
    }
}
