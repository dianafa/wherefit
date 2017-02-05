package com.diana.wherefit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

public class SportActivityItemClickListener implements OnItemClickListener {


    private final AdapterView listView;
    private Context context;
    private SportActivitiesService service;

    public SportActivityItemClickListener(Context context, AdapterView listView, SportActivitiesService service) {
        this.context = context;
        this.listView = listView;
        this.service = service;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SportActivity activity = (SportActivity) listView.getItemAtPosition(position);
        final Place place = service.getPlace(activity.getPlaceId());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("Nawiguj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (place != null) {
                    navigateTo(place.getLocation());
                }
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Dialog closes
            }
        });

        builder.setMessage(Html.fromHtml(activity.getDescription()))
                .setTitle(activity.getName());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void navigateTo(Location l) {
        String uri = "http://maps.google.com/maps?daddr=" + l.getLatitude() + "," + l.getLongitude();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }
}
