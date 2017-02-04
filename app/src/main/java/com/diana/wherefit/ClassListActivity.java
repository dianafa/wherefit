package com.diana.wherefit;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;
import com.diana.wherefit.pojo.SportActivity;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ClassListActivity extends AppCompatActivity {
    private static final float DEFAULT_DISTANCE = 10_000f; // 10km

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        Location location = getIntent().getParcelableExtra("LOCATION");

        initSportActivities(SportActivitiesServiceImpl.getInstance(), location);
    }

    private void initSportActivities(SportActivitiesService service, Location location) {
        List<SportActivity> activities;

        if (service != null) {
            Calendar now = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            to.add(Calendar.DAY_OF_MONTH, 3); // 3 days ahead
            activities = service.getActivities(location, DEFAULT_DISTANCE, now.getTimeInMillis(), to.getTimeInMillis());
            Collections.sort(activities, new SportActivityTimeComparator());
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(new SportActivityArrayAdapter(this, activities, service));
            listView.setOnItemClickListener(new SportActivityItemClickListener(this, listView, service));
        }
    }
}
