package com.diana.wherefit.impl;
import com.diana.wherefit.api.SportActivityApi;
import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;
import com.diana.wherefit.pojo.Timeframe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class FabrykaFormyApi implements SportActivityApi {
    private final String url = "http://www.fabryka-formy.pl/kluby/poznan-galeria-posnania";
    private Document doc;

    public FabrykaFormyApi(String filepath) {
        File file = new File(filepath);
        try {
            doc = Jsoup.parse(file, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FabrykaFormyApi() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<SportActivity> getActivitiesForWeekday(int weekday) {
        Elements allDays = doc.select(".tt_timetable ul.tt_items_list");
        Element dayTable = allDays.get(weekday);

        Elements activitiesTable = dayTable.select("li");
        Collection<SportActivity> activities = new ArrayList<>();


        for (Element activityElement: activitiesTable) {
            activities.add(createSportActivityFromElement(activityElement, weekday));
        }

        return activities;
    }

    public SportActivity createSportActivityFromElement(Element element, int weekday) {
        String name = element.select("span").first().html();
        String hoursString = element.select(".value").first().ownText();
        Timeframe hours  = getTimesFromString(hoursString, weekday);

        return new SportActivity(name, 1, hours.getStartDate(), hours.getEndDate(), "opis");
    }

    private Timeframe getTimesFromString(String hours, int weekday) {
        Calendar today = Calendar.getInstance();

        return new Timeframe(today.getTimeInMillis(), hours, weekday);
    }

    @Override
    public Collection<SportActivity> getActivities(long from, long to) {
        Elements daysElements = doc.select(".tt_timetable ul.tt_items_list");
        Collection<SportActivity> activities = new ArrayList<>();

        for (int i = 0; i < daysElements.size(); i++) {
            Elements activitiesDom = daysElements.get(i).select("li");
            for (Element activityElement: activitiesDom) {
                SportActivity activity = createSportActivityFromElement(activityElement, i);
                if (activity.getStartTime() > from && activity.getEndTime() < to) {
                    activities.add(activity);
                }
            }
        }

        return activities;
    }

    @Override
    public Collection<Place> getPlaces() {
        ArrayList<Place> places = new ArrayList<>();

        places.add(new Place(1, "Fabryka Formy - Posnania", 52.3971177, 16.9538029));

        return places;
    }
}
