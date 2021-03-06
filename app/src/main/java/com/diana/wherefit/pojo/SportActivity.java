package com.diana.wherefit.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SportActivity implements Serializable {

    private static final String HOUR_FORMAT = "kk:mm";
    private static final String DATE_FORMAT = "dd.MM.yy";
    private String name;
    private int placeId;
    private long startTime;
    private long endTime;
    private String description;

    public SportActivity() {
        // empty
    }

    public SportActivity(String name, int placeId, long startTime, long endTime, String description) {
        this.name = name;
        this.placeId = placeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

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
        return new SimpleDateFormat(HOUR_FORMAT, Locale.getDefault()).format(new Date(time));
    }

    private String getDate(long time) {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date(time));
    }
    
    public Integer getDay() {
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(startTime);
        return day.get(Calendar.DAY_OF_WEEK);
    }

    public String getDialogTitle() {
        return getDate(startTime) + " " + getName();
    }

    public String getType() {
        if (name.contains("AQUA")) {
            return "Aquagym";
        }

        if (name.contains("Zumba")) {
            return "Zumba";
        }

        if (name.toLowerCase().contains("cross")) {
            return "Crossfit";
        }

        if (name.toLowerCase().contains("les mills")) {
            return "Les Mills";
        }

        return name;
    }

    public String getDescription() {
        return description;
    }
}
