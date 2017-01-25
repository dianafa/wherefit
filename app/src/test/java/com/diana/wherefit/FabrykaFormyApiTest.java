package com.diana.wherefit;
import com.diana.wherefit.api.FabrykaFormyApi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FabrykaFormyApiTest {
    private FabrykaFormyApi ff;

    @Before
    public void setUp() throws Exception {
        this.ff = new FabrykaFormyApi();
    }

    @Test
    public void readActivitiesList() throws Exception {
        String expected = "<div></div>";

        assertNotNull(ff);

        String allEvents = ff.getAllEventsElement().html();

        assertNotNull(allEvents);
        assertEquals(allEvents, expected);
    }
}