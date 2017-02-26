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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RemplusApi implements SportActivityApi {
    private final String url = "http://www.remplus.pl/index.php/aerobik-grafik";
    private Document doc;

    public RemplusApi(String filepath) {
        File file = new File(filepath);
        try {
            doc = Jsoup.parse(file, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RemplusApi() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SportActivity createSportActivityFromElement(Element element, int weekday) {
        String name = element.select("span").first().html();
        String hoursString = element.select(".value").first().ownText();
        Timeframe hours  = getTimesFromString(hoursString, weekday);
        Elements descriptionElementChildren = doc.select("#all-events table span.event_header[title=" + name + "]");
        String description = "";

        for (Element descriptionElementChild : descriptionElementChildren) {
            String topHour = descriptionElementChild.parent().select(".top_hour .hours").first().ownText();
            if (Objects.equals(topHour, new SimpleDateFormat("kk.mm", Locale.getDefault()).format(new Date(hours.getStartDate())))) {
                description = descriptionElementChild.parent().select(".after_hour_text").first().html();
            }
        }

        return new SportActivity(name, 1, hours.getStartDate(), hours.getEndDate(), description);
    }

    private Timeframe getTimesFromString(String hours, int weekday) {
        Calendar today = Calendar.getInstance();

        return new Timeframe(today.getTimeInMillis(), hours, weekday);
    }

    @Override
    public Collection<SportActivity> getActivities(long from, long to) {
        Elements activityRows = doc.select(".timetable tbody tr");
        Collection<SportActivity> activities = new ArrayList<>();

        for (Element row: activityRows) {
            if (row.ownText().length() > 5) { // row is worth processing - is not empty
                int colspanSum = 0;
                Elements activitiesElements = row.select("td[colspan]");
                for (Element activityElement: activitiesElements) {
                    colspanSum += Integer.parseInt(row.attr("colspan"));

                    int calendarWeekday = (((int)Math.floor(colspanSum / 6) + 1) % 7 + 1); // Calendar.DAY_OF_WEEK returns values 1-7 where 1 is Sunday
                    SportActivity activity = createSportActivityFromElement(activityElement, calendarWeekday);

                    if (activity.getStartTime() > from && activity.getEndTime() < to) {
                        activities.add(activity);
                    }
                }
            }

        }

        return activities;
    }

    @Override
    public Collection<Place> getPlaces() {
        ArrayList<Place> places = new ArrayList<>();

        places.add(new Place(2, "Remplus", 52.3971177, 16.9538029));

        return places;
    }
}
