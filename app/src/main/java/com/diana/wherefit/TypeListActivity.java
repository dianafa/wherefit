package com.diana.wherefit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;

import java.util.Calendar;

public class TypeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);

        SportActivitiesService service = SportActivitiesServiceImpl.getInstance();

        Calendar now = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        to.add(Calendar.DAY_OF_MONTH, 3); // 3 days ahead

        ListView listView = (ListView) findViewById(R.id.checkbox_list);
        listView.setAdapter(new SportTypeArrayAdapter(this, service.getTypes(now.getTimeInMillis(), to.getTimeInMillis()), service));
        //listView.setOnItemClickListener(new SportActivityItemClickListener(this, listView, service));
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        int id = view.getId();

        System.out.println("clicked: " + id);
    }
}