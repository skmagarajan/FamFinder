package com.example.famfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Creategrp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategrp);
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"Netflix","Spotify"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        //get the spinner from the xml.
        Spinner dropdown2 = findViewById(R.id.spinner2);

        String[] items2 = new String[]{"1","2","3","4"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);

        dropdown2.setAdapter(adapter2);

    }
}
