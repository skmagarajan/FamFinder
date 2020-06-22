package com.example.famfinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FriendRequest_GroupHead extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> grpName;
    private final ArrayList<String> vacancy;

    public FriendRequest_GroupHead(Activity context, ArrayList grpName, ArrayList vacancy){
        super(context,R.layout.friendrequest,grpName);

        this.context = context;
        this.grpName = grpName;
        this.vacancy = vacancy;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.friendrequest,null,true);

        TextView grp = (TextView) rowView.findViewById(R.id.grp_name);
        TextView vac = (TextView) rowView.findViewById(R.id.vacancy);

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)rowView.findViewById(R.id.delete_btn);
        Button addBtn = (Button)rowView.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ADD",Toast.LENGTH_LONG).show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"DELETE",Toast.LENGTH_LONG).show();
            }
        });



        grp.setText(grpName.get(position));
        vac.setText(vacancy.get(position));
        return rowView;
    }
}
