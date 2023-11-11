package com.example.m_hikeappjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomeObAdapter extends RecyclerView.Adapter<CustomeObAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList id_ob, name_ob, time_ob, description_ob, id_hike_ob;
    String idH;

    CustomeObAdapter(Activity activity, Context context, ArrayList id_ob, ArrayList name_ob, ArrayList time_ob, ArrayList description_ob, ArrayList id_hike_ob, String idH) {
        this.activity = activity;
        this.context = context;
        this.id_ob = id_ob;
        this.name_ob = name_ob;
        this.time_ob = time_ob;
        this.description_ob = description_ob;
        this.id_hike_ob = id_hike_ob;
        this.idH = idH;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mrowob, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
         holder.name_txt_ob.setText(String.valueOf(name_ob.get(position)));
         holder.ob_id_txt.setText(String.valueOf(id_ob.get(position)));
         holder.time_txt_ob.setText(String.valueOf(time_ob.get(position)));
         holder.mainLayoutOb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i  = new Intent(context, UpdateActivityObser.class);

                 i.putExtra("id_ob", String.valueOf(id_ob.get(position)));
                 i.putExtra("name_ob", String.valueOf(name_ob.get(position)));
                 i.putExtra("time_ob", String.valueOf(time_ob.get(position)));
                 i.putExtra("description_ob", String.valueOf(description_ob.get(position)));
                 i.putExtra("id_hike_ob", String.valueOf(id_hike_ob.get(position)));
                 i.putExtra("id_hike", idH);


                 activity.startActivityForResult(i, 1);

             }
         });
    }



    @Override
    public int getItemCount() {
        return id_ob.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ob_id_txt, name_txt_ob, time_txt_ob;
        LinearLayout mainLayoutOb;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            ob_id_txt = itemView.findViewById(R.id.ob_id_txt);
            name_txt_ob = itemView.findViewById(R.id.name_txt_ob);
            time_txt_ob = itemView.findViewById(R.id.time_txt_ob);
            mainLayoutOb = itemView.findViewById(R.id.mainLayoutRowOb);

        }
    }
}
