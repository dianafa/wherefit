package com.diana.wherefit;

import java.io.Serializable;
import java.util.List;

public class SportActivities implements Serializable {

    private List<SportActivity> activities;

    public List<SportActivity> getActivities() {
        return activities;
    }
}
