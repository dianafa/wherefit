package com.diana.wherefit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.List;

class SportActivityArrayAdapter extends ArrayAdapter<SportActivity> {

    private final SportActivitiesService service;

    SportActivityArrayAdapter(Context context, List<SportActivity> activities, SportActivitiesService service) {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, activities);
        this.service = service;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
        SportActivity activity = getItem(position);
        if (activity != null) {
            text1.setText(activity.getHeader());
            Place place = service.getPlace(activity.getPlaceId());
            if (place != null) {
                text2.setText(place.getName());
            }
        }
        return view;
    }
}
