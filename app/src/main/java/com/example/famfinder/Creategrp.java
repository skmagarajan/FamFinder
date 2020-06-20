package com.example.famfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Creategrp extends AppCompatActivity {

    private final String TAG = "Create GRP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategrp);
        //get the spinner from the xml.
        final Spinner dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"Netflix","Spotify"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        //get the spinner from the xml.
        final Spinner dropdown2 = findViewById(R.id.spinner2);

        String[] items2 = new String[]{"1","2","3","4"};

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);

        dropdown2.setAdapter(adapter2);

        final EditText grpname = (EditText) findViewById(R.id.grpname);

        Button create = (Button) findViewById(R.id.create);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> user = new HashMap<>();
                String _grpname = grpname.getText().toString(); //converting to String
                String application = dropdown.getSelectedItem().toString();
                String vacancy = dropdown2.getSelectedItem().toString();
                Map<String, String> GRP = new HashMap<>();
                GRP.put("vacancy",vacancy);
                Bundle b = getIntent().getExtras();
                String mail_id = b.getString("MailID");
                GRP.put("head",mail_id);
                System.out.println(_grpname.length());
                if(_grpname.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please enter group name",Toast.LENGTH_LONG).show();
                }
                else{
                    db.collection(application).document(_grpname).set(GRP)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot");
                                }
                            });
                    Toast.makeText(getApplicationContext(),"Hurray !!! Group created successfully",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
