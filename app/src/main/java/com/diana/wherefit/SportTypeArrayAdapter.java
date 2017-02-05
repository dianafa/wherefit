package com.diana.wherefit;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import com.diana.wherefit.api.SportActivitiesService;

        import java.util.List;

class SportTypeArrayAdapter extends ArrayAdapter<String> {
    SportTypeArrayAdapter(Context context, List<String> types, SportActivitiesService service) {
        super(context, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, types);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        String type = getItem(position);
        if (type != null) {
            text1.setText(type);
        }
        return view;
    }
}

