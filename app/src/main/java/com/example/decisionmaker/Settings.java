package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    Spinner resultView;
    Spinner algorithm;
    String[] resultViews = {"Pie chart", "Histogram"};
    String[] algorithms = {"Electre I", "Electre II"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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


        algorithm = findViewById(R.id.settingsAlgorithmSpinner);
        ArrayAdapter<String> algorithmAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,algorithms);
        algorithmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        algorithm.setAdapter(algorithmAdapter);
    }
}