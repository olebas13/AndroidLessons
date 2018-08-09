package com.olebas.p0381sqlitetransaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    DBHelper dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "--- onCreate Activity ---");
        dbh = new DBHelper(this);
        myActions();
    }

    private void myActions() {
       try {
           db = dbh.getWritableDatabase();
           delete(db, "mytable");

           db.beginTransaction();
           insert(db, "mytable", "val1");

           Log.d(LOG_TAG, "create DBHelper");
           DBHelper dbh2 = new DBHelper(this);
           Log.d(LOG_TAG, "get db");
           SQLiteDatabase db2 = dbh2.getWritableDatabase();
           read(db2, "mytable");
           dbh2.close();

            db.setTransactionSuccessful();
            db.endTransaction();

            read(db, "mytable");
            dbh.close();
       } catch (Exception ex) {
           Log.d(LOG_TAG, ex.getClass() + " error: " + ex.getMessage());
       }
    }

    private void read(SQLiteDatabase db, String table) {
        Log.d(LOG_TAG, "Read table " + table);
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c != null) {
            Log.d(LOG_TAG, "Records count = " + c.getCount());
            if (c.moveToFirst()) {
                do {
                    Log.d(LOG_TAG, c.getString(c.getColumnIndex("val")));
                } while (c.moveToNext());
            }
            c.close();
        }
    }

    private void insert(SQLiteDatabase db, String table, String value) {
        Log.d(LOG_TAG, "Insert in table " + table + " value = " + value);
        ContentValues cv = new ContentValues();
        cv.put("val", value);
        db.insert(table, null, cv);
    }

    private void delete(SQLiteDatabase db, String table) {
        Log.d(LOG_TAG, "Delete all from table " + table);
        db.delete(table, null, null);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate Database ---");

            db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "val text"
                + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
