package com.olebas.p0371sqliteinnerjoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public final String LOG_TAG = "myLogs";

    public int[] position_id = {1, 2, 3, 4};
    public String[] position_name = {"Директор", "Программер", "Бухгалтер", "Охранник"};
    public int[] position_salary = {15000, 13000, 10000, 8000};

    public String[] people_name = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь"};
    public int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(this);
    }
}
