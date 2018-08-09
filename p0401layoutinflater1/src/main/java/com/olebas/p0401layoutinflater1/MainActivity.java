package com.olebas.p0401layoutinflater1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.*;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater ltInflater = getLayoutInflater();
        View view = ltInflater.inflate(R.layout.text, null, false);
        LayoutParams lp = view.getLayoutParams();

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        linLayout.addView(view);

        Log.d(LOG_TAG, "Class of view: " + view.getClass().toString());
        Log.d(LOG_TAG, "LayoutParams of view is null: " + (lp == null));
        Log.d(LOG_TAG, "Text of view: " + ((TextView) view).getText());
    }
}
