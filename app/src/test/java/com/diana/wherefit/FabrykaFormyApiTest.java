package com.diana.wherefit;
import com.diana.wherefit.impl.FabrykaFormyApi;
import com.diana.wherefit.pojo.SportActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class FabrykaFormyApiTest {
    private FabrykaFormyApi ff;

    @Before
    public void setUp() throws Exception {
        this.ff = new FabrykaFormyApi();
    }

    @Test
    public void getActivitiesForWeekdayTest() {
        Collection<SportActivity> tuesdayActivities = ff.getActivitiesForWeekday(1);

        assertEquals(13, tuesdayActivities.size());
        assertEquals("Pilates", ((SportActivity)tuesdayActivities.toArray()[5]).getName());
    }

    @Test
    public void createSportActivityFromElement1() {
        String html = "<li class=\"timetable_clearfix icon_clock_black\"><span title=\"ABS\">ABS</span><div class=\"value\"><div class=\"befhr\"></div>08.00 - 08.30 </div></li>";
        Element e = Jsoup.parse(html);
        SportActivity a = ff.createSportActivityFromElement(e, 1);

        assertEquals("ABS", a.getName());
    }

    @Test
    public void createSportActivityFromElement2() {
        String html = "<li class=\"timetable_clearfix icon_clock_black\"><span title=\"ABS\">ABS</span><div class=\"value\"><div class=\"befhr\"></div>08.00 - 08.30 </div></li>";
        Element e = Jsoup.parse(html);
        SportActivity a = ff.createSportActivityFromElement(e, 1);

        assertEquals("ABS", a.getName());
    }
}