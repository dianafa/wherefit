package com.diana.wherefit;

import android.content.Context;
import android.graphics.Color;
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
            Place place = service.getPlace(activity.getPlaceId());

            view.setBackgroundColor(Color.parseColor(getColorFromActivity(activity)));
            text1.setText(activity.getHeader());
            if (place != null) {
                text2.setText(place.getName());
            }
        }

        return view;
    }

    /**
     * @param activity SportActivity
     * @return string in form of #RRGGBB or #AARRGGBB
     */
    private String getColorFromActivity(SportActivity activity) {
        String hex = Integer.toHexString(activity.getDay() * 2);

        return "#D09" + hex + hex + "F" + hex + "F";

    }
}
