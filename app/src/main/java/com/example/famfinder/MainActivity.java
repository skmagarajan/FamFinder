package com.example.famfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
        Button signup = (Button) findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Page = new Intent(MainActivity.this,Options.class);
                startActivity(Page);
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
