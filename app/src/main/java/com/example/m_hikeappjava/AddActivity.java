package com.example.m_hikeappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.view.View;


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
public class AddActivity extends AppCompatActivity {
    AutoCompleteTextView parkingAutoComView, levelAutoComView;
    TextInputLayout dateLayout, parkingLayout, levelLayout, nameLayout, locationLayout, lengthLayout;
    EditText dateTime, name, location, lengthH, description;

    String[] pitems = {"Yes", "No"};
    String[] litems = {"LOW", "MEDIUM", "HIGH"};

    Button create;

    ArrayAdapter<String> pAdapter, levAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        parkingAutoComView = findViewById(R.id.parking);
        levelAutoComView = findViewById(R.id.level);
        dateLayout = findViewById(R.id.wrapTime);
        parkingLayout = findViewById(R.id.wrapParking);
        levelLayout = findViewById(R.id.wrapLevel);
        nameLayout = findViewById(R.id.wrapName);
        locationLayout = findViewById(R.id.wrapLocation);
        lengthLayout = findViewById(R.id.wrapLength);

        pAdapter = new ArrayAdapter<String>(this, R.layout.list_items, pitems);

        levAdapter = new ArrayAdapter<String>(this, R.layout.list_items, litems);

        parkingAutoComView.setAdapter(pAdapter);
        levelAutoComView.setAdapter(levAdapter);

        dateTime = findViewById(R.id.dateH);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        lengthH = findViewById(R.id.numLen);
        description = findViewById(R.id.description);

        create = findViewById(R.id.btnCreate);
        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDateTime(dateTime);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateTime() | !validateParking() | !validateLevel() | !validateName() | !validateLocation() | !validateLength()) {
                    Toast.makeText(AddActivity.this, "Creeted fail!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddActivity.this,"Created Successfully!!!",Toast.LENGTH_LONG).show();
                }
            }
        });









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
        if (name.getEditableText().toString().isEmpty()) {
            name.setError("");
            nameLayout.setHelperText("Please enter the name !");
            return false;
        } else {
            name.setError(null);
            nameLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateLocation () {
        if (location.getEditableText().toString().isEmpty()) {
            location.setError("");
            locationLayout.setHelperText("Please enter the date !");
            return false;
        } else {
            location.setError(null);
            locationLayout.setHelperText("");
            return true;
        }
    }
    private boolean validateLength () {
        if (lengthH.getEditableText().toString().isEmpty()) {
            lengthH.setError("");
            lengthLayout.setHelperText("Please enter the date !");
            return false;
        } else {
            lengthH.setError(null);
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