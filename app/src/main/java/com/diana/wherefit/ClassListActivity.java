package com.diana.wherefit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;

public class ClassListActivity extends AppCompatActivity {

    private ListView listView;

    private Gson gson = new Gson();
    private Places places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = (ListView) findViewById(R.id.list);

        Reader reader = new InputStreamReader(getResources().openRawResource(R.raw.activities));
        final SportActivities activities = gson.fromJson(reader, SportActivities.class);
        reader = new InputStreamReader(getResources().openRawResource(R.raw.places));
        this.places = gson.fromJson(reader, Places.class);
        Collections.sort(activities.getActivities());
        ArrayAdapter<SportActivity> adapter = new ArrayAdapter<SportActivity>(this, android.R.layout.simple_list_item_2, android.R.id.text1, activities.getActivities()) {
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                SportActivity activity = (SportActivity) listView.getItemAtPosition(position);
                text1.setText(activity.getHeader());
                Place place = places.getForId(activity.getPlaceId());
                text2.setText(place.getName());
                return view;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SportActivity activity = (SportActivity) listView.getItemAtPosition(position);

                //Show Alert
                Toast.makeText(getApplicationContext(), activity.getName() , Toast.LENGTH_LONG).show();
            }

        });

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_class_list);
        layout.addView(textView);
    }
}
