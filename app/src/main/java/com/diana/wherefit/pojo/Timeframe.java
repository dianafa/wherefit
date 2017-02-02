package com.diana.wherefit.pojo;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Timeframe {
    private long startDate;
    private long endDate;

    public Timeframe(long todayDate, String times, int weekday) {
        String pattern = "^(\\d{1,2})[:.]?(\\d{2})?[\\s\\-]*(\\d{1,2})[:.]?(\\d{2})?";
        String startHourString = "0",
                endHourString = "0",
                startMinuteString = "0",
                endMinuteString = "0";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(times);

        if (m.find()) {
            startHourString = m.group(1);
            startMinuteString = m.group(2);
            endHourString = m.group(3);
            endMinuteString = m.group(4);
        } else {
            System.out.println("nie bangla czas taki: " + times);
        }

        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(todayDate);

        // find the difference between today and the closest date with given day of week
        int daysGap = Math.abs(weekday - today.get(Calendar.DAY_OF_WEEK));

        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(todayDate);

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(todayDate);

        start.add(Calendar.DAY_OF_WEEK, daysGap);
        end.add(Calendar.DAY_OF_WEEK, daysGap);
        start.set(Calendar.HOUR, Integer.parseInt(startHourString));
        end.set(Calendar.HOUR, Integer.parseInt(endHourString));
        if (startMinuteString != null) {
            start.set(Calendar.MINUTE, Integer.parseInt(startMinuteString));
        }
        if (endMinuteString != null) {
            end.set(Calendar.MINUTE, Integer.parseInt(endMinuteString));
        }

        startDate = start.getTimeInMillis();
        endDate = end.getTimeInMillis();
    }

    public long getEndDate() {
        return endDate;
    }

    public long getStartDate() {
        return startDate;
    }
}
