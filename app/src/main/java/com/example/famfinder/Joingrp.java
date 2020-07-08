package com.example.famfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Request{
    public String message;
    public String user;
    public String group_name;
    public Request(String message,String user,String group_name){
        this.message = message;
        this.user = user;
        this.group_name = group_name;
    }
}
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

                final EditText edittext = new EditText(Joingrp.this);

                grps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final Map<String,String> request = new HashMap<>();
                        Toast.makeText(getApplicationContext(),mailID.get(position),Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Joingrp.this);
                        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision. If YES, Please enter your comments below:")
                        .setView(edittext);
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Bundle b = getIntent().getExtras();
                                                String mail_id = b.getString("MailID"); //Getting the current user email-ID
                                                String task = String.valueOf(edittext.getText()); //Getting the input task
//                                                request.put("request", new Request(task,mail_id,grp_name.get(position)));
                                                request.put("message",task);
                                                request.put("requester_mail",mail_id);
                                                request.put("group_name",grp_name.get(position));
                                                System.out.println(mailID.get(position));
//                                                db.collection("Users").document(mail_id)
//                                                        .collection("Activities").document("acitivity1").set(data)
                                                //creating a subcollection inside user.
                                                db.collection("users").document(mailID.get(position))
                                                        .collection("requests").document(grp_name.get(position)).set(request)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot");
                                                    }
                                                });
                                                Toast.makeText(getApplicationContext(),"REQUEST SENT WITH UR COMMENTS"+task,Toast.LENGTH_LONG).show();
                                            }
                                        });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        }
        });
    }
}
