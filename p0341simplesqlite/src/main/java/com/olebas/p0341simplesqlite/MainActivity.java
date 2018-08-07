package com.olebas.p0341simplesqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private final String LOG_TAG = "myLogs";

    private Button btnAdd, btnRead, btnClear;
    private EditText etName, etEmail;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnClear = (Button) findViewById(R.id.btnClear);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {
        ContentValues cv = new ContentValues();

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (view.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable ---");

                cv.put("name", name);
                cv.put("email", email);

                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable ---");
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex));
                    } while (c.moveToNext());
                } else {
                    Log.d(LOG_TAG, "0 rows");
                }

                c.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable ---");
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
        }

        dbHelper.close();
    }
}
