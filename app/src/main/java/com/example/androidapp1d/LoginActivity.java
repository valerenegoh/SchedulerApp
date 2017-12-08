package com.example.androidapp1d;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.androidapp1d.Prof.ProfFeedActivity;
import com.example.androidapp1d.Stud.Booking.StudProfDetails;

public class LoginActivity extends AppCompatActivity {
    private Button loginbutton;
    private Button tempbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        loginbutton = (Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentActivity = new Intent(LoginActivity.this, StudProfDetails.class);
                startActivity(studentActivity);
            }
        });

        tempbutton=(Button) findViewById(R.id.loginprof);
        tempbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profActivity = new Intent(LoginActivity.this, ProfFeedActivity.class);
                startActivity(profActivity);
            }
        });
    }
}
