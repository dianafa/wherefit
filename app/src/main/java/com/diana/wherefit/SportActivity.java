package com.diana.wherefit;

import java.io.Serializable;
import java.util.Calendar;

public class SportActivity implements Comparable<SportActivity>, Serializable {

    private Calendar calendar = Calendar.getInstance();

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

    private String getHour(long time) {
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
    }

    @Override
    public int compareTo(SportActivity o) {
        return Long.compare(this.getStartTime(), o.getStartTime());
    }
}
