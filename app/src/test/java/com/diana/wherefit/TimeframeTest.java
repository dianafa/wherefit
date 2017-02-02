package com.diana.wherefit;

import com.diana.wherefit.pojo.Timeframe;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class TimeframeTest {
    @Test
    public void constructorTest() throws Exception {

        String[] testCases = {
            "8-9",
            "8 - 9",
            "8:00-9",
            "8-9:00",
            "08-09",
            "08-09:00",
            "08:00-09:00",
            "8.00-9",
            "8.00-9.00",
            "8.00-9:00"
        };

        Calendar now = new GregorianCalendar(2017, 0, 30); // 30 Jan 2017 00:00
        Calendar expectedStart = new GregorianCalendar(2017, 1, 1, 8, 0); //1st Feb 2017 8:00
        Calendar expectedEnd = new GregorianCalendar(2017, 1, 1, 9, 0); //1st Feb 2017 9:00

        for (String testCase : testCases) {
            System.out.println("Test case: " + testCase);
            Timeframe time = new Timeframe(now.getTimeInMillis(), testCase, Calendar.WEDNESDAY);

            Calendar actualStart = Calendar.getInstance();
            actualStart.setTimeInMillis(time.getStartDate());

            Calendar actualEnd = Calendar.getInstance();
            actualEnd.setTimeInMillis(time.getEndDate());

            assertEquals(expectedStart.get(Calendar.MONTH), actualStart.get(Calendar.MONTH));
            assertEquals(expectedStart.get(Calendar.DAY_OF_MONTH), actualStart.get(Calendar.DAY_OF_MONTH));
            assertEquals(expectedStart.get(Calendar.HOUR), actualStart.get(Calendar.HOUR));
            assertEquals(expectedStart.get(Calendar.MINUTE), actualStart.get(Calendar.MINUTE));

            assertEquals(expectedEnd.get(Calendar.MONTH), actualEnd.get(Calendar.MONTH));
            assertEquals(expectedEnd.get(Calendar.DAY_OF_MONTH), actualEnd.get(Calendar.DAY_OF_MONTH));
            assertEquals(expectedEnd.get(Calendar.HOUR), actualEnd.get(Calendar.HOUR));
            assertEquals(expectedEnd.get(Calendar.MINUTE), actualEnd.get(Calendar.MINUTE));

            assertEquals(expectedStart.getTimeInMillis(),time.getStartDate());
            assertEquals(expectedEnd.getTimeInMillis(), time.getEndDate());
        }
    }

    @Test
    public void constructorTestMinutes() throws Exception {

        String[] testCases = {
                "8:30-9",
                "8.30 - 9",
                "8:30-9",
                "8:30-9:00",
                "08:30-09",
                "08.30-09:00",
                "08:30-09:00",
                "8.30-9",
                "8.30-9.00",
                "8.30-9:00"
        };

        Calendar now = new GregorianCalendar(2017, 0, 30); // 30 Jan 2017 00:00
        Calendar expectedStart = new GregorianCalendar(2017, 1, 1, 8, 30); //1st Feb 2017 8:30
        Calendar expectedEnd = new GregorianCalendar(2017, 1, 1, 9, 00); //1st Feb 2017 9:00

        for (String testCase : testCases) {
            System.out.println("Test case: " + testCase);
            Timeframe time = new Timeframe(now.getTimeInMillis(), testCase, Calendar.WEDNESDAY);

            Calendar actualStart = Calendar.getInstance();
            actualStart.setTimeInMillis(time.getStartDate());

            Calendar actualEnd = Calendar.getInstance();
            actualEnd.setTimeInMillis(time.getEndDate());

            assertEquals(expectedStart.get(Calendar.MONTH), actualStart.get(Calendar.MONTH));
            assertEquals(expectedStart.get(Calendar.DAY_OF_MONTH), actualStart.get(Calendar.DAY_OF_MONTH));
            assertEquals(expectedStart.get(Calendar.HOUR), actualStart.get(Calendar.HOUR));
            assertEquals(expectedStart.get(Calendar.MINUTE), actualStart.get(Calendar.MINUTE));

            assertEquals(expectedEnd.get(Calendar.MONTH), actualEnd.get(Calendar.MONTH));
            assertEquals(expectedEnd.get(Calendar.DAY_OF_MONTH), actualEnd.get(Calendar.DAY_OF_MONTH));
            assertEquals(expectedEnd.get(Calendar.HOUR), actualEnd.get(Calendar.HOUR));
            assertEquals(expectedEnd.get(Calendar.MINUTE), actualEnd.get(Calendar.MINUTE));

            assertEquals(expectedStart.getTimeInMillis(),time.getStartDate());
            assertEquals(expectedEnd.getTimeInMillis(), time.getEndDate());
        }
    }

}
