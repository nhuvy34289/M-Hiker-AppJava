package com.example.m_hikeappjava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class MyDbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASENAME = "Hikedt.d";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "hike_table";

    private static final String COLUMN_ID = "id_hike";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_PARKING = "parking_available";
    private static final String COLUMN_LEN = "length_hike";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_DES = "description";



    public MyDbHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
         String query = "CREATE TABLE " + TABLE_NAME + "("
                 + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + COLUMN_NAME + " TEXT, " +
                 COLUMN_LOCATION + " TEXT, " +
                 COLUMN_TIME + " TEXT, " +
                 COLUMN_PARKING + " TEXT, " +
                 COLUMN_LEN + " NUMERIC, " +
                 COLUMN_LEVEL + " TEXT, " +
                 COLUMN_DES + " TEXT);";

        db.execSQL(query);

    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int i, int i1) {
       db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
    }
}
