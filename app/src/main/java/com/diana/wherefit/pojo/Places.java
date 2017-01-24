package com.diana.wherefit.pojo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Places implements Serializable {

    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public Places() {
        places = Collections.emptyList();
    }
}
