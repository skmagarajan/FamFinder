package com.example.famfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Options extends AppCompatActivity {

    String current_user ;//Contains UserName

    ListView grps;//Listview

    String TAG = "Options.JAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button Create_Group = (Button) findViewById(R.id.Create_Group);

        Button Join_Group = (Button) findViewById(R.id.Join_Group);

        Button Manage_Group = (Button) findViewById(R.id.Manage_group);

        Bundle b = getIntent().getExtras();
        current_user = b.getString("MailID");

        Create_Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage = new Intent(Options.this,Creategrp.class);
                Bundle b = getIntent().getExtras();
                nextPage.putExtras(b);
                startActivity(nextPage);
            }
        });

        Join_Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage = new Intent(Options.this,Joingrp.class);
                Bundle b = getIntent().getExtras();
                nextPage.putExtras(b);
                startActivity(nextPage);
            }
        });

        Manage_Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage = new Intent(Options.this,ManageGroup.class);
//                Intent friend_req_page = new Intent(Options.this,FriendRequest_GroupHead.class);
                Bundle b = getIntent().getExtras();
                nextPage.putExtras(b);
                startActivity(nextPage);
//                friend_req_page.putExtras(b);
//                startActivity(friend_req_page);
            }
        });

        //Groups Custom ListView

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<String> grp_name = new ArrayList<>();
        final ArrayList<String> vacancy = new ArrayList<>();
        final ArrayList<String> mailID = new ArrayList<>();

        Log.i(TAG,current_user);

        db.collection("users").document(current_user).collection("GroupsOwned")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                grp_name.add(document.getId());
//                                Map<String, Object> x = new HashMap<>();
//                                x = document.getData();
//                                String vvv = x.get("vacancy").toString();
//                                String vvvv = x.get("head").toString();
//                                vacancy.add(vvv);
//                                mailID.add(vvvv);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            Log.w(TAG, grp_name.toString());
                            GroupName adapter = new GroupName(Options.this, grp_name);
                            grps = (ListView) findViewById(R.id.options_listview);
                            grps.setAdapter(adapter);

                        }
                    }
                });
    }
}

class GroupName extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> grpName;
//    private final ArrayList<String> vacancy;
    public GroupName(Activity context, ArrayList grpName) {
        super(context, R.layout.join_grp_list, grpName);

        this.context = context;
        this.grpName = grpName;
//        this.vacancy = vacancy;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.main_grpname, null, true);

        TextView grp = (TextView) rowView.findViewById(R.id.grp_name);
//        TextView vac = (TextView) rowView.findViewById(R.id.vacancy);

        grp.setText(grpName.get(position));
//        vac.setText(vacancy.get(position));
        return rowView;
    }
}


