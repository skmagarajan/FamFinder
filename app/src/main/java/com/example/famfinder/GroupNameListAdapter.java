package com.example.famfinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GroupNameListAdapter extends ArrayAdapter {

    private final Activity context;
    private final ArrayList<String> grpName;
    private final ArrayList<String> vacancy;

    public GroupNameListAdapter(Activity context, ArrayList grpName, ArrayList vacancy){
        super(context,R.layout.join_grp_list,grpName);

        this.context = context;
        this.grpName = grpName;
        this.vacancy = vacancy;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.join_grp_list,null,true);

        TextView grp = (TextView) rowView.findViewById(R.id.grp_name);
        TextView vac = (TextView) rowView.findViewById(R.id.vacancy);

        grp.setText(grpName.get(position));
        vac.setText(vacancy.get(position));
        return rowView;
    }
}
