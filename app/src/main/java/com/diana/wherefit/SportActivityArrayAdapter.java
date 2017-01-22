package com.diana.wherefit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.Places;
import com.diana.wherefit.pojo.SportActivities;
import com.diana.wherefit.pojo.SportActivity;

public class SportActivityArrayAdapter extends ArrayAdapter<SportActivity> {

    private final Places places;

    public SportActivityArrayAdapter(Context context, SportActivities activities, Places places) {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, activities.getActivities());
        this.places = places;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);
        SportActivity activity = getItem(position);
        if (activity != null) {
            text1.setText(activity.getHeader());
            Place place = places.getForId(activity.getPlaceId());
            if (place != null) {
                text2.setText(place.getName());
            }
        }
        return view;
    }
}
