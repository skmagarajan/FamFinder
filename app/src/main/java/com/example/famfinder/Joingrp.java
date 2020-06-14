package com.example.famfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Joingrp extends AppCompatActivity {

    private final String TAG = "JoinGrp";

    ListView grps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingrp);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final ArrayList<String> grp_name = new ArrayList<>();
        final ArrayList<String> vacancy = new ArrayList<>();
        final ArrayList<String> mailID = new ArrayList<>();

        db.collection("Netflix")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    grp_name.add(document.getId());
                    Map<String,Object> x = new HashMap<>();
                    x = document.getData();
                    String vvv = x.get("vacancy").toString();
                    String vvvv = x.get("head").toString();
                    vacancy.add(vvv);
                    mailID.add(vvvv);
                    //Log.d(TAG, document.getId() + " => " + document.getData());
                }
                Log.w(TAG,grp_name.toString());
                GroupNameListAdapter adapter=new GroupNameListAdapter(Joingrp.this,grp_name,vacancy);
                grps = (ListView) findViewById(R.id.list);
                grps.setAdapter(adapter);

                grps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getApplicationContext(),mailID.get(position),Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        }
        });


    }
}
