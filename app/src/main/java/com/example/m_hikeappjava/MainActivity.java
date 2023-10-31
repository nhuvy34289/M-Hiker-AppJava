package com.example.m_hikeappjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.ArrayList;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_btn;
    MyDbHelper myDb;
    ArrayList<String> id_hike, name, location, time, parking_available, length_hike, level, description;

    CustomeAdapter customeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerView);
        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent = new Intent(MainActivity.this, AddActivity.class);
                  startActivity(intent);
            }
        });

        myDb = new MyDbHelper(MainActivity.this);
        id_hike = new ArrayList<>();
        name = new ArrayList<>();
        location = new ArrayList<>();
        time = new ArrayList<>();
        parking_available = new ArrayList<>();
        length_hike = new ArrayList<>();
        level = new ArrayList<>();
        description = new ArrayList<>();

        storeDb();

        customeAdapter = new CustomeAdapter(MainActivity.this, this, id_hike, name, location, time, parking_available, length_hike, level, description);
        recyclerView.setAdapter(customeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    void storeDb() {
        Cursor cursor = myDb.readAllData();

        if(cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No data!", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                id_hike.add(cursor.getString(0));
                name.add(cursor.getString(1));
                location.add(cursor.getString(2));
                time.add(cursor.getString(3));
                parking_available.add(cursor.getString(4));
                length_hike.add(cursor.getString(5));
                level.add(cursor.getString(6));
                description.add(cursor.getString(7));
            }
        }
    }
}