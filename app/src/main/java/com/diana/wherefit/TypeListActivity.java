package com.diana.wherefit;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.diana.wherefit.api.SportActivitiesService;
import com.diana.wherefit.impl.SportActivitiesServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;

public class TypeListActivity extends AppCompatActivity implements OnClickListener {
    ListView listView;
    SportTypeArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);

        SportActivitiesService service = SportActivitiesServiceImpl.getInstance();

        Calendar now = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        to.add(Calendar.DAY_OF_MONTH, 3); // 3 days ahead

        listView = (ListView) findViewById(R.id.checkbox_list);
        Button button = (Button) findViewById(R.id.gobutton);
        button.setOnClickListener(this);
        adapter = new SportTypeArrayAdapter(this, service.getTypes(now.getTimeInMillis(), to.getTimeInMillis()), service);
        listView.setAdapter(adapter);
        //listView.setOnItemClickListener(new SportActivityItemClickListener(this, listView, service));
    }

    private void showAllFromTypes(ArrayList<String> selectedTypes) {
        Intent intent = new Intent(this, ClassListActivity.class);

        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArrayList("types", selectedTypes);

        // Add the bundle to the intent.
        intent.putExtras(b);
        startActivity(intent);
    }

    public void onClick(View view) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(checked.keyAt(i)));
        }

        showAllFromTypes(selectedItems);
    }
}