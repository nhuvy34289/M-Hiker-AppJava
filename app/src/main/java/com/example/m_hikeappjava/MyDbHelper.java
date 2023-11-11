package com.example.m_hikeappjava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.widget.Toast;
import android.database.Cursor;


import androidx.annotation.Nullable;

class MyDbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASENAME = "Hikedt.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "hike_table";
    private static final String TABLE_NAME_OB = "observation_table";

    private static final String COLUMN_ID = "id_hike";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_PARKING = "parking_available";
    private static final String COLUMN_LEN = "length_hike";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_DES = "description";

    private static final String COLUMN_ID_OB = "id_ob";
    private static final String COLUMN_NAME_OB = "name_ob";
    private static final String COLUMN_TIME_OB = "time_ob";
    private static final String COLUMN_DES_OB = "description_ob";
    private static final String COLUMN_ID_HIKE_OB = "id_hike_ob" ;



    public MyDbHelper(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
         String query = "CREATE TABLE " + TABLE_NAME + " ("
                 + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + COLUMN_NAME + " TEXT, " +
                 COLUMN_LOCATION + " TEXT, " +
                 COLUMN_TIME + " TEXT, " +
                 COLUMN_PARKING + " TEXT, " +
                 COLUMN_LEN + " INTEGER, " +
                 COLUMN_LEVEL + " TEXT, " +
                 COLUMN_DES + " TEXT);";
         String queryOb = "CREATE TABLE " + TABLE_NAME_OB + " ("
                 + COLUMN_ID_OB + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                 + COLUMN_NAME_OB + " TEXT, " +
                 COLUMN_TIME_OB + " TEXT, " +
                 COLUMN_DES_OB + " TEXT, " +
                 COLUMN_ID_HIKE_OB + " INTEGER NOT NULL);";


        db.execSQL(query);
        db.execSQL(queryOb);
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME_OB);
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readObservationsOfHike (int idH) {
        String query = "SELECT * FROM " + TABLE_NAME_OB + " WHERE id_hike_ob=?";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[] {String.valueOf(idH)});
        }
        return cursor;
    }

    void addHike (String name, String location, String time, String parking, int lengthH, String level, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PARKING, parking);
        cv.put(COLUMN_LEN, lengthH);
        cv.put(COLUMN_LEVEL, level);
        cv.put(COLUMN_DES, description);

        long results = db.insert(TABLE_NAME, null, cv);

        if (results == -1) {
            Toast.makeText(context, "Fail Created!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Insert DB successfully!", Toast.LENGTH_LONG).show();
        }

    }

    void addObser (String name, String time, String description, int idH) {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();


         cv.put(COLUMN_NAME_OB, name);
         cv.put(COLUMN_TIME_OB, time);
         cv.put(COLUMN_DES_OB, description);
         cv.put(COLUMN_ID_HIKE_OB, idH);

        long rels = db.insert(TABLE_NAME_OB, null, cv);
        if (rels == -1) {
            Toast.makeText(context, "Fail Created!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Insert DB successfully!", Toast.LENGTH_LONG).show();
        }

    }

    void editHike (String row_id,String name, String location, String time, String parking, int lengthH, String level, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_PARKING, parking);
        cv.put(COLUMN_LEN, lengthH);
        cv.put(COLUMN_LEVEL, level);
        cv.put(COLUMN_DES, description);


        long results = db.update(TABLE_NAME, cv, "id_hike=?", new String[]{row_id});

        if (results == 0) {
            Toast.makeText(context, "Fail Created!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Updated DB successfully!", Toast.LENGTH_LONG).show();
        }

    }
    void editOb(String row_id, String name, String time, String description, int id_hikeOb) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_OB, name);
        cv.put(COLUMN_TIME_OB, time);
        cv.put(COLUMN_DES_OB, description);
        cv.put(COLUMN_ID_HIKE_OB, id_hikeOb);

        long res = db.update(TABLE_NAME_OB, cv, "id_ob=?", new String[] {row_id});

        if (res == 0) {
            Toast.makeText(context, "Fail Created!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Updated DB successfully!", Toast.LENGTH_LONG).show();
        }
    }

    void deleteOne(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long results = db.delete(TABLE_NAME, "id_hike=?", new String[]{row_id});
        long resdel2 = db.delete(TABLE_NAME_OB, "id_hike_ob=?", new String[]{row_id});

        if (results == 0 && resdel2 == 0) {
            Toast.makeText(context, "Fail Deleted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Deleted one successfully!", Toast.LENGTH_LONG).show();
        }
    }

    void deleteOneOb(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long results = db.delete(TABLE_NAME_OB, COLUMN_ID_OB + "=?", new String[]{row_id});
        System.out.println(results);

        if (results == 0) {
            Toast.makeText(context, "Fail Deleted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Deleted one successfully!", Toast.LENGTH_LONG).show();
        }
    }
}
