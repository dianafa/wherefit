package com.diana.wherefit.api;
import android.location.Location;

import com.diana.wherefit.pojo.SportActivities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class FabrykaFormyApi {
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

    public SportActivities getActivities(Location location) {
        return new SportActivities();
    }

    public Elements getAllEventsElement() {
        return doc.select("#all-events");
    }

}
