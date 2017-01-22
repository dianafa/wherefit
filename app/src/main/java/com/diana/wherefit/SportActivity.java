package com.diana.wherefit;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SportActivity implements Comparable<SportActivity>, Serializable {

    private static final String FORMAT = "hh:mm";

    private String name;

    private int placeId;

    private long startTime;

    private long endTime;

    public String getName() {
        return name;
    }

    public int getPlaceId() {
        return placeId;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getHeader() {
        return getHour(startTime) + " - " + getHour(endTime) + " " + getName();
    }

    @Override
    public int compareTo(@NonNull SportActivity o) {
        return Long.compare(this.getStartTime(), o.getStartTime());
    }

    private String getHour(long time) {
        return new SimpleDateFormat(FORMAT, Locale.getDefault()).format(new Date(time));
    }
}
