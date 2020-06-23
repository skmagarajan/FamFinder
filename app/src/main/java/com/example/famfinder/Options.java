package com.example.famfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Button Create_Group = (Button) findViewById(R.id.Create_Group);

        Button Join_Group = (Button) findViewById(R.id.Join_Group);

        Button Manage_Group = (Button) findViewById(R.id.Manage_group);

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
    }
}
