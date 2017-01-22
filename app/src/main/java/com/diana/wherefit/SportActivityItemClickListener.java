package com.diana.wherefit;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.diana.wherefit.pojo.SportActivity;

public class SportActivityItemClickListener implements OnItemClickListener {


    private Context context;

    private final AdapterView listView;

    public SportActivityItemClickListener(Context context, AdapterView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SportActivity activity = (SportActivity) listView.getItemAtPosition(position);
        //Show Alert
        Toast.makeText(context.getApplicationContext(), activity.getName() , Toast.LENGTH_LONG).show();

    }
}
