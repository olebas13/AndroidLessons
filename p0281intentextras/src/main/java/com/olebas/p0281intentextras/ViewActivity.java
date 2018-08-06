package com.olebas.p0281intentextras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    private TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        tvView = (TextView) findViewById(R.id.tvView);
        Intent intent = getIntent();

        String fname = intent.getStringExtra("fname");
        String lname = intent.getStringExtra("lname");

        tvView.setText("Your Name is: " + fname + " " + lname);
    }
}
