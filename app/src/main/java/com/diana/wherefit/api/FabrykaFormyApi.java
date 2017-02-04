package com.diana.wherefit.api;
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

    public Elements getAllEventsElement() {
        return doc.select("#all-events");
    }

    public Collection<SportActivity> getActivitiesForWeekday(int weekday) {
        Elements allDays = doc.select(".tt_timetable ul.tt_items_list");
        Element dayTable = allDays.get(weekday);

        Elements activitiesTable = dayTable.select("li");
        Collection<SportActivity> activities = new ArrayList<>();


        for (Element activityElement: activitiesTable) {
            activities.add(createSportActivityFromElement(activityElement));
        }

        return activities;
    }

    public SportActivity createSportActivityFromElement(Element element) {
        String name = element.select("span").first().html();
        String hoursString = element.select(".value").first().ownText();
        Timeframe hours  = getTimesFromString(hoursString);

        return new SportActivity(name, 1, hours.getStartDate(), hours.getEndDate(), "opis");
    }

    private Timeframe getTimesFromString(String hours) {
        Calendar today = Calendar.getInstance();

        return new Timeframe(today.getTimeInMillis(), hours, today.get(Calendar.DAY_OF_WEEK));
    }

    @Override
    public Collection<SportActivity> getActivities(long from, long to) {
        Elements activitiesDom = doc.select(".tt_timetable ul.tt_items_list li");
        Collection<SportActivity> activities = new ArrayList<>();

        for (Element activityElement: activitiesDom) {
            activities.add(createSportActivityFromElement(activityElement));
        }

        return activities;
    }

    @Override
    public Collection<Place> getPlaces() {
        ArrayList<Place> places = new ArrayList<>();

        places.add(new Place(1, "Fabryka Formy - Posnania", 52.4039071, 16.9500184));

        return places;
    }
}
