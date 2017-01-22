package com.diana.wherefit.pojo;

import java.io.Serializable;
import java.util.List;

public class Places implements Serializable {

    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public Place getForId(int id) {
        Place place = null;
        for (Place p: places) {
            if (p.getId() == id) {
                place = p;
                break;
            }
        }
        return place;
    }
}
