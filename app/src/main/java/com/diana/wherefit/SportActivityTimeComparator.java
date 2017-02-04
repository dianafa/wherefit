package com.diana.wherefit;

import com.diana.wherefit.pojo.SportActivity;

import java.util.Comparator;

public class SportActivityTimeComparator implements Comparator<SportActivity> {
    @Override
    public int compare(SportActivity o1, SportActivity o2) {
        return Long.compare(o1.getStartTime(), o2.getStartTime());
    }
}
