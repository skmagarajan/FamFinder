package com.example.famfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int RC_SIGN_IN = 0;
    private String TAG = "Login Page";
    SignInButton g_signin;
    GoogleSignInClient mGoogleSignInClient;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            System.out.println("Success");
            Intent Options = new Intent(MainActivity.this,Options.class);
            Bundle b = new Bundle();
            b.putString("MailID","Saravanan");
            Options.putExtras(b);
            startActivity(Options);
            Toast.makeText(getApplicationContext(),"Opening",Toast.LENGTH_LONG).show();
        } catch (ApiException e) {

            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*****************ToolBar*********************/
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        /*****************Setting Toolbar with hamburger*********************/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, (Toolbar) findViewById(R.id.toolbar), R.string.activity_main_drawer_open, R.string.activity_main_drawer_close);
        toggle.syncState();
        /*****************Setting Listener on Navigation Bar*********************/
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button login = (Button) findViewById(R.id.login);
        final Button signup = (Button) findViewById(R.id.signup);
        final EditText mail = (EditText) findViewById(R.id.mailid);
        final EditText password = (EditText) findViewById(R.id.password);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        g_signin = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        g_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail_id = mail.getText().toString();
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
                                    Bundle b = new Bundle();
                                    b.putString("MailID",mail_id);
                                    Options.putExtras(b);
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

    /*****************Click events on Hamburger option !*********************/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.sign_out: {
                Intent Options = new Intent(MainActivity.this,Options.class);
                Bundle b = new Bundle();
                b.putString("MailID","mail_id");
                Options.putExtras(b);
                startActivity(Options);
                Toast.makeText(getApplicationContext(),"Opening",Toast.LENGTH_LONG).show();
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
