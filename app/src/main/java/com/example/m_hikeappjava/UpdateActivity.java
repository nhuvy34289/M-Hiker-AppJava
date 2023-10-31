package com.example.m_hikeappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.TextView;



import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;



import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;
public class UpdateActivity extends AppCompatActivity {
    AutoCompleteTextView parkingAutoComView, levelAutoComView;
    TextInputLayout dateLayout, parkingLayout, levelLayout, nameLayout, locationLayout, lengthLayout;
    EditText dateTime, name_input, location_input, lengthH_input, description_input;

    String[] pitems = {"Yes", "No"};
    String[] litems = {"LOW", "MEDIUM", "HIGH"};

    Button update;

    ArrayAdapter<String> pAdapter, levAdapter;

    String id_hike, name, location, time, parking_available, length_hike, level, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        parkingAutoComView = findViewById(R.id.parking1);
        levelAutoComView = findViewById(R.id.level1);
        dateLayout = findViewById(R.id.wrapTime1);
        parkingLayout = findViewById(R.id.wrapParking1);
        levelLayout = findViewById(R.id.wrapLevel1);
        nameLayout = findViewById(R.id.wrapName1);
        locationLayout = findViewById(R.id.wrapLocation1);
        lengthLayout = findViewById(R.id.wrapLength1);

        pAdapter = new ArrayAdapter<String>(this, R.layout.list_items, pitems);

        levAdapter = new ArrayAdapter<String>(this, R.layout.list_items, litems);

        parkingAutoComView.setAdapter(pAdapter);
        levelAutoComView.setAdapter(levAdapter);

        dateTime = findViewById(R.id.dateH1);
        name_input = findViewById(R.id.name1);
        location_input = findViewById(R.id.location1);
        lengthH_input = findViewById(R.id.numLen1);
        description_input = findViewById(R.id.description1);

        getIntentData();




        update = findViewById(R.id.btnUpdate);
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDateTime(dateTime);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateTime() | !validateParking() | !validateLevel() | !validateName() | !validateLocation() | !validateLength()) {
                    Toast.makeText(UpdateActivity.this, "Creeted fail!", Toast.LENGTH_LONG).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
                    View customeLayout = getLayoutInflater().inflate(R.layout.dialog, null);
                    builder.setView(customeLayout);
                    builder.setTitle("Entered Details");

                    String enteredName = name_input.getText().toString();
                    String enteredLocation = location_input.getText().toString();
                    String enteredDateTime = dateTime.getText().toString();
                    String enteredParking = parkingAutoComView.getText().toString();
                    String enteredLen = lengthH_input.getText().toString();
                    String enteredLevel = levelAutoComView.getText().toString();
                    String enteredDes = description_input.getText().toString();
                    TextView resDel = customeLayout.findViewById(R.id.mhikeVals_details);


                    String result = "Name of the hike:\t"+enteredName+"\nLocation:\t"
                            +enteredLocation+"\nDate of the hike:\t"+enteredDateTime
                            +"\nLength of the hike:\t"+ enteredLen
                            +"\nDefficulty Level:\t"+enteredLevel
                            +"\nDescription:\t"+enteredDes;

                    resDel.setText(result);

                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MyDbHelper mydb = new MyDbHelper(UpdateActivity.this);

                            mydb.editHike(id_hike, enteredName, enteredLocation, enteredDateTime, enteredParking, Integer.valueOf(enteredLen), enteredLevel, enteredDes);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(UpdateActivity.this,"Cancelled!!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            }
        });




    }

    void getIntentData() {
        if(getIntent().hasExtra("id_hike") && getIntent().hasExtra("name") && getIntent().hasExtra("location") && getIntent().hasExtra("time")
          && getIntent().hasExtra("parking_available") && getIntent().hasExtra("length_hike") && getIntent().hasExtra("level") && getIntent().hasExtra("description")) {
            id_hike = getIntent().getStringExtra("id_hike");
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            time = getIntent().getStringExtra("time");
            parking_available = getIntent().getStringExtra("parking_available");
            length_hike = getIntent().getStringExtra("length_hike");
            level = getIntent().getStringExtra("level");
            description = getIntent().getStringExtra("description");

            //set val edit
            name_input.setText(name);
            location_input.setText(location);
            dateTime.setText(time);
            parkingAutoComView.setText(parking_available);
            lengthH_input.setText(length_hike);
            levelAutoComView.setText(level);
            description_input.setText(description);

        } else {
            Toast.makeText(UpdateActivity.this,"No data!!!",Toast.LENGTH_LONG).show();

        }
    }
    private void showDialogDateTime(EditText dateTime){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        dialog.show();
    }

    private boolean validateTime () {
        if (dateTime.getEditableText().toString().isEmpty()) {
            dateTime.setError("");
            dateLayout.setHelperText("Please enter the date !");
            return false;
        } else {
            dateTime.setError(null);
            dateLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateName () {
        if (name_input.getEditableText().toString().isEmpty()) {
            name_input.setError("");
            nameLayout.setHelperText("Please enter the name !");
            return false;
        } else {
            name_input.setError(null);
            nameLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateLocation () {
        if (location_input.getEditableText().toString().isEmpty()) {
            location_input.setError("");
            locationLayout.setHelperText("Please enter the date !");
            return false;
        } else {
            location_input.setError(null);
            locationLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateLength () {
        if (lengthH_input.getEditableText().toString().isEmpty()) {
            lengthH_input.setError("");
            lengthLayout.setHelperText("Please enter the date !");
            return false;
        } else {
            lengthH_input.setError(null);
            lengthLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateParking () {
        if(parkingAutoComView.getText().toString().isEmpty()) {
            parkingLayout.setHelperText("Please choose any parking option !");
            return false;
        } else {
            parkingLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateLevel () {
        if(levelAutoComView.getText().toString().isEmpty()) {
            levelLayout.setHelperText("Please choose any parking option !");
            return false;
        } else {
            levelLayout.setHelperText("");
            return true;
        }
    }
}