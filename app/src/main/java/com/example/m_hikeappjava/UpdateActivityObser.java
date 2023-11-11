package com.example.m_hikeappjava;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateActivityObser extends AppCompatActivity{

    TextInputLayout nameLayOutOb, dateLayoutOb;
    EditText description_obser, name_obser, time_obser;
    Button createOb, deleteOb;

    String id_ob, name_ob, time_ob, description_ob, id_hike_ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);

        nameLayOutOb = findViewById(R.id.wrapName_obser_update);
        dateLayoutOb = findViewById(R.id.wrapTime_obser_update);
        description_obser = findViewById(R.id.description_obser_update);
        name_obser = findViewById(R.id.name_obser_update);
        time_obser = findViewById(R.id.dateH_obser_update);





        time_obser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDateTime(time_obser);
            }
        });

        createOb = findViewById(R.id.btnEdit_obser);


       getIntentData();

        createOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateName() | !validateTime()) {
                    Toast.makeText(UpdateActivityObser.this, "Created fail !!!", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivityObser.this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
                    View customeLayout = getLayoutInflater().inflate(R.layout.dialog, null);
                    builder.setView(customeLayout);
                    builder.setTitle("Entered Observation Details");

                    String enteredName = name_obser.getText().toString();
                    String enteredDateTime = time_obser.getText().toString();
                    String enteredDes = description_obser.getText().toString();
                    TextView resDel = customeLayout.findViewById(R.id.mhikeVals_details);


                    String result = "Name of the observation:\t"+enteredName
                            +"\nDate of the observation:\t"+enteredDateTime
                            +"\nDescription:\t"+enteredDes;

                    resDel.setText(result);

                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MyDbHelper mydb = new MyDbHelper(UpdateActivityObser.this);
                            mydb.editOb(id_ob, enteredName, enteredDateTime, enteredDes, Integer.valueOf(id_hike_ob));

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(UpdateActivityObser.this,"Cancelled!!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        deleteOb = findViewById(R.id.btnDel_obser);
        deleteOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivityObser.this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
                View customeLayout = getLayoutInflater().inflate(R.layout.dialog, null);
                builder.setView(customeLayout);
                builder.setTitle("Entered Details");
                builder.setMessage("You want to delete " + name_ob + " ?");

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MyDbHelper mydb = new MyDbHelper(UpdateActivityObser.this);
                        mydb.deleteOneOb(id_ob);
                        finish();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UpdateActivityObser.this,"Cancelled!!!",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    void getIntentData() {
        System.out.println(getIntent().getStringExtra("id_hike"));
        if(getIntent().hasExtra("id_ob") && getIntent().hasExtra("name_ob") && getIntent().hasExtra("time_ob") && getIntent().hasExtra("description_ob") && getIntent().hasExtra("id_hike_ob")) {
            name_ob = getIntent().getStringExtra("name_ob");
            id_ob = getIntent().getStringExtra("id_ob");
            time_ob = getIntent().getStringExtra("time_ob");
            description_ob = getIntent().getStringExtra("description_ob");
            id_hike_ob = getIntent().getStringExtra("id_hike_ob");

            name_obser.setText(name_ob);
            time_obser.setText(time_ob);
            description_obser.setText(description_ob);

        } else {
            Toast.makeText(UpdateActivityObser.this,"No data!!!",Toast.LENGTH_LONG).show();
        }
    }

    private void showDialogDateTime(EditText time_obser){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                time_obser.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        dialog.show();
    }

    private boolean validateTime () {
        if (time_obser.getEditableText().toString().isEmpty()) {
            time_obser.setError("");
            dateLayoutOb.setHelperText("Please enter the date !");
            return false;
        } else {
            time_obser.setError(null);
            dateLayoutOb.setHelperText("");
            return true;
        }
    }
    private boolean validateName () {
        if (name_obser.getEditableText().toString().isEmpty()) {
            name_obser.setError("");
            nameLayOutOb.setHelperText("Please enter the name !");
            return false;
        } else {
            name_obser.setError(null);
            nameLayOutOb.setHelperText("");
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ObservationActivity.class);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }
}
