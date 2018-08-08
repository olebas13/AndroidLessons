package com.olebas.p0361sqlitequery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private final String LOG_TAG = "myLogs";

    String[] name = {"Китай", "США", "Бразилия", "Япония", "Германия", "Египет", "Италия", "Франция", "Украина", "Канада"};
    int[] people = {1400, 311, 195, 128, 82, 80, 60, 66, 45, 35};
    String[] region = {"Азия", "Америка", "Америка", "Азия", "Европа", "Африка", "Европа", "Европа", "Европа", "Америка"};

    private Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
    private EditText etFunc, etPeople, etRegionPeople;
    private RadioGroup rgSort;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnFunc = (Button) findViewById(R.id.btnFunc);
        btnPeople = (Button) findViewById(R.id.btnPeople);
        btnSort = (Button) findViewById(R.id.btnSort);
        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnHaving = (Button) findViewById(R.id.btnHaving);

        etFunc = (EditText) findViewById(R.id.etFunc);
        etPeople = (EditText) findViewById(R.id.etPeople);
        etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        btnAll.setOnClickListener(this);
        btnFunc.setOnClickListener(this);
        btnPeople.setOnClickListener(this);
        btnSort.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
        btnHaving.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query("mytable", null, null, null, null, null, null);
        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < 10; i++) {
                cv.put("name", name[i]);
                cv.put("people", people[i]);
                cv.put("region", region[i]);
                Log.d(LOG_TAG, "id = " + db.insert("mytable", null, cv));
            }
        }
        c.close();
        dbHelper.close();

        onClick(btnAll);
    }

    @Override
    public void onClick(View view) {
        db = dbHelper.getWritableDatabase();

        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor c = null;

        switch (view.getId()) {
            case R.id.btnAll:
                Log.d(LOG_TAG, "--- Все записи ---");
                c = db.query("mytable", null, null, null, null, null, null);
                break;
            case R.id.btnFunc:
                Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                columns = new String[] { sFunc };
                c = db.query("mytable", columns, null, null, null, null, null);
                break;
            case R.id.btnPeople:
                Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[] { sPeople };
                c = db.query("mytable", null, selection, selectionArgs, null, null, null);
                break;
            case R.id.btnGroup:
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                c = db.query("mytable", columns, null, null, groupBy, null, null);
                break;
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople + " ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                c = db.query("mytable", columns, null, null, groupBy, having, null);
                break;
            case R.id.btnSort:
                switch (rgSort.getCheckedRadioButtonId()) {
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    case R.id.rPeople:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                c = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                            + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
            c.close();
        } else {
            Log.d(LOG_TAG, "Cursor is null");
        }
        dbHelper.close();
    }
}
