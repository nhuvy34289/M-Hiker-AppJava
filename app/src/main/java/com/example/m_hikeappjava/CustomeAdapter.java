package com.example.m_hikeappjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList id_hike, name, location, time, parking_available, length_hike, level, description;

    CustomeAdapter(Activity activity,Context context, ArrayList id_hike, ArrayList name, ArrayList location, ArrayList time, ArrayList parking_available, ArrayList length_hike, ArrayList level, ArrayList description) {
         this.activity = activity;
         this.context = context;
         this.id_hike = id_hike;
         this.name = name;
         this.location = location;
         this.time = time;
         this.parking_available = parking_available;
         this.length_hike = length_hike;
         this.level = level;
         this.description = description;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

         holder.hike_id_txt.setText(String.valueOf(id_hike.get(position)));
         holder.name_txt.setText(String.valueOf(name.get(position)));
         holder.time_txt.setText(String.valueOf(time.get(position)));
         holder.button_more.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(context, ObservationActivity.class);

                 intent.putExtra("id_hike", String.valueOf(id_hike.get(position)));

                 activity.startActivityForResult(intent, 1);
             }
         });
         holder.mainLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               Intent intent = new Intent(context, UpdateActivity.class);

                 intent.putExtra("id_hike", String.valueOf(id_hike.get(position)));
                 intent.putExtra("name", String.valueOf(name.get(position)));
                 intent.putExtra("location", String.valueOf(location.get(position)));
                 intent.putExtra("time", String.valueOf(time.get(position)));
                 intent.putExtra("parking_available", String.valueOf(parking_available.get(position)));
                 intent.putExtra("length_hike", String.valueOf(length_hike.get(position)));
                 intent.putExtra("level", String.valueOf(level.get(position)));
                 intent.putExtra("description", String.valueOf(description.get(position)));
                 activity.startActivityForResult(intent, 1);


                 System.out.println(String.valueOf(length_hike.get(position)));


             }
             });
    }

    @Override
    public int getItemCount() {
        return id_hike.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView hike_id_txt, name_txt, time_txt;
        Button button_more;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            hike_id_txt = itemView.findViewById(R.id.hike_id_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            button_more = itemView.findViewById(R.id.button_more);
            mainLayout = itemView.findViewById(R.id.mainLayoutRow);

        }
    }

}