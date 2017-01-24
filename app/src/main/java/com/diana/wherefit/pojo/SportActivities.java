package com.diana.wherefit.pojo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SportActivities implements Serializable {

    private List<SportActivity> activities;

    public SportActivities() {
        activities = Collections.emptyList();
    }

    public List<SportActivity> getActivities() {
        return activities;
    }
}
