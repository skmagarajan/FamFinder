package com.example.famfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Login Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
        final Button signup = (Button) findViewById(R.id.signup);
        final EditText mail = (EditText) findViewById(R.id.mailid);
        final EditText password = (EditText) findViewById(R.id.password);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail_id = mail.getText().toString();
//                System.out.println(db.collection("users").getId().);
//                Log.d(TAG,"DocumentSnapshot data: " + mail_id);
                DocumentReference docRef = db.collection("users").document(mail_id);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

//                            Map<String,Object> data_received = new HashMap<>();
//                            data_received = document.getData();
//                            for(Map.Entry<String,Object> entry : data_received.entrySet()){
//                                System.out.println(entry.getKey().getClass().getName());
//                                if (entry.getKey().toString() == "password"){
//                                    System.out.println("s"+entry.getValue());
//                                }
//                            }
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData().get("password"));
                                if(document.getData().get("password").equals(password.getText().toString())){
                                    Intent Options = new Intent(MainActivity.this,Options.class);
                                    startActivity(Options);
                                    Toast.makeText(getApplicationContext(),"Opening",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Please enter correct password",Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Enter correct password");
                                }
                            } else {
                                Log.d(TAG, "No such document");
                                Toast.makeText(getApplicationContext(),"Incorrect password or emailID",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
//                db.collection("users")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//                                    }
//                                } else {
//                                    Log.w(TAG, "Error getting documents.", task.getException());
//                                }
//                            }
//                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Page = new Intent(MainActivity.this,SignUp.class);
                startActivity(Page);
            }
        });
    }
}
