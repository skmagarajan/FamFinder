package com.example.famfinder;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;



public class FriendRequest_GroupHead extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<String> user;
    private final ArrayList<String> message;
    private final ArrayList<String> grpName;
    private final String owner;
    private final String TAG = "FriendRequest";

    private Button deleteBtn;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FriendRequest_GroupHead(Activity context, ArrayList user, ArrayList message,ArrayList grpName,String owner){
        super(context,R.layout.friendrequest,user);

        this.context = context;
        this.user = user;
        this.message = message;
        this.grpName = grpName;
        this.owner = owner;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        final int pos = position;
        final String UserName = user.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.friendrequest,null,true);

        TextView grp = (TextView) rowView.findViewById(R.id.grp_name);
        TextView vac = (TextView) rowView.findViewById(R.id.vacancy);

        //Handle buttons and add onClickListeners
        deleteBtn = (Button)rowView.findViewById(R.id.delete_btn);
        Button addBtn = (Button)rowView.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> GRP = new HashMap<>();
                GRP.put("GroupName",grpName.get(pos));
                GRP.put("head",owner);
                db.collection("users").document(UserName).collection("GroupsOwned").document(grpName.get(pos))
                        .set(GRP, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot");
                            }
                        });
                //db.collection("Netflix").document(grpName.get(pos)).
                Toast.makeText(getContext(),"Added Successfully",Toast.LENGTH_LONG).show();
                db.collection("users").document(owner).collection("requests")
                        .document(UserName)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Deleted Successful");
                        rowView.setVisibility(rowView.GONE);//Remove notification from frontend
                    }
                });

                //clear(); //Clears the entire screen
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                System.out.println(UserName);
                db.collection("users").document(owner).collection("requests")
                        .document(UserName)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Deleted Successful");
                        rowView.setVisibility(rowView.GONE);//Remove notification from frontend
                    }
                });
                Toast.makeText(getContext(),"DELETE",Toast.LENGTH_LONG).show();
                //clear(); //clears the entire screen
            }
        });
        grp.setText(user.get(position));
        vac.setText(message.get(position));
        return rowView;
    }
}
