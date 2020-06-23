package com.example.famfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageGroup extends AppCompatActivity {

    private final String TAG = "Manage_Group";
    ListView grps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<String> user = new ArrayList<>();
        final ArrayList<String> message = new ArrayList<>();
        final ArrayList<String> grpName = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        final String owner_mail_id = b.getString("MailID"); //Getting the current user email-ID
        db.collection("users").document(owner_mail_id)
                .collection("requests").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        System.out.println("Here");
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                user.add(document.getId());
                                Map<String,Object> details = new HashMap<>();
                                details = document.getData();
                                System.out.println(details);
                                message.add(details.get("message").toString());
                                grpName.add(details.get("group_name").toString());
//                                String vvvv = details.get("head").toString();
                            }
                            System.out.println(user);
                            FriendRequest_GroupHead adapter=new FriendRequest_GroupHead(ManageGroup.this,user,message,grpName,owner_mail_id);
                            grps = (ListView) findViewById(R.id.list);
                            grps.setAdapter(adapter);
                        }
                    }
                });
    }
}