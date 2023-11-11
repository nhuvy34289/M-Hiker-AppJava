package com.example.m_hikeappjava;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ObservationActivity extends AppCompatActivity {
    FloatingActionButton add_btn_ob;
    RecyclerView recyclerView;
    ArrayList<String> id_ob, name_ob, time_ob, description_ob, id_hike_ob;
    MyDbHelper myDbHelper;
    CustomeObAdapter customeObAdapter;
    String idHike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_observation);

        add_btn_ob = findViewById(R.id.add_btn_obser);

        recyclerView = findViewById(R.id.recyclerViewObser);


        add_btn_ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservationActivity.this, AddActivityObser.class);
                intent.putExtras(getIntent());
                startActivity(intent);
                finish();
            }
        });

        idHike = getIntent().getStringExtra("id_hike");


        myDbHelper = new MyDbHelper(ObservationActivity.this);
        id_ob = new ArrayList<>();
        name_ob = new ArrayList<>();
        time_ob = new ArrayList<>();
        description_ob = new ArrayList<>();
        id_hike_ob = new ArrayList<>();

        storeDbOb(idHike);

        customeObAdapter = new CustomeObAdapter(ObservationActivity.this, this, id_ob, name_ob, time_ob, description_ob, id_hike_ob, idHike);
        recyclerView.setAdapter(customeObAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ObservationActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
             recreate();
        }
    }


    void storeDbOb(String idHike) {
        System.out.println(idHike);

        Cursor cursor = myDbHelper.readObservationsOfHike(Integer.valueOf(idHike));

        if(cursor.getCount() == 0) {
            Toast.makeText(ObservationActivity.this, "No data!", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                id_ob.add(cursor.getString(0));
                name_ob.add(cursor.getString(1));
                time_ob.add(cursor.getString(2));
                description_ob.add(cursor.getString(3));
                id_hike_ob.add(cursor.getString(4));
            }
        }
    }
}
