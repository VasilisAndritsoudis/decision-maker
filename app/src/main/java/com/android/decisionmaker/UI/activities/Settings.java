package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.decisionmaker.R;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    Spinner resultView;
    String[] resultViews = {"Pie chart", "Histogram"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        resultView = findViewById(R.id.settingsResultSpinner);
        ArrayAdapter<String> resultAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,resultViews);
        resultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        resultView.setAdapter(resultAdapter);
        resultView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}