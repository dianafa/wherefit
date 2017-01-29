package com.diana.wherefit.api;

import com.diana.wherefit.pojo.Place;
import com.diana.wherefit.pojo.SportActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        //String startTime = activityElement.select(".top_hour .hours").first().html();
        //String endTime = activityElement.select(".bottom_hour .hours").first().html();
        SportActivity a = new SportActivity(name, 2, getTimeFromString("string"), getTimeFromString("string"), "opis");

        return a;
    }

    private long getTimeFromString(String hour) {

        return 54535L;
    }

    @Override
    public Collection<SportActivity> getActivities() {
        Elements activitiesDom = doc.select(".tt_timetable ul.tt_items_list li");
        Collection<SportActivity> activities = new ArrayList<>();

        for (Element activityElement: activitiesDom) {
            activities.add(createSportActivityFromElement(activityElement));
        }

        return activities;
    }

    @Override
    public Collection<Place> getPlaces() {
        return Collections.emptyList();
    }
}
