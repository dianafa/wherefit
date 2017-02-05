package com.diana.wherefit.utils;

import java.util.Calendar;

public final class TimeUtil {

    private TimeUtil() {
        // don't instantiate it
    }

    public static boolean isBetween(Calendar activityStart, Calendar activityEnd, Calendar calendarFrom, Calendar calendarTo) {
        return activityStart.after(calendarFrom) && activityEnd.before(calendarTo);
    }
}
