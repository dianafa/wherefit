package com.diana.wherefit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import java.util.Collection;
import java.util.List;

public class SportActivityArrayAdapter extends ArrayAdapter<SportActivity> {

    private final Collection<Place> places;

    public SportActivityArrayAdapter(Context context, List<SportActivity> activities, Collection<Place> places) {
        super(context, android.R.layout.simple_list_item_2, android.R.id.text1, activities);
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
            Place place = getForId(activity.getPlaceId());
            if (place != null) {
                text2.setText(place.getName());
            }
        }
        return view;
    }

    private Place getForId(int id) {
        Place place = null;
        for (Place p : places) {
            if (p.getId() == id) {
                place = p;
                break;
            }
        }
        return place;
    }
}
