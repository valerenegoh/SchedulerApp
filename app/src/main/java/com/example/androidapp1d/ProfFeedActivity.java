package com.example.androidapp1d;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProfFeedActivity extends AppCompatActivity{
    private TextView feedstub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profactivitity_feed);
    }
}
