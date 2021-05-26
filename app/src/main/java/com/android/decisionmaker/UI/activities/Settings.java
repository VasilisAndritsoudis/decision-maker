package com.android.decisionmaker.UI.activities;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.handlers.DBHandler;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    Spinner resultView;
    String[] resultViews = {"Pie chart", "Histogram"};
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        resultView = findViewById(R.id.settingsResultSpinner);
        ArrayAdapter<String> resultAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, resultViews);
        resultAdapter.setDropDownViewResource(R.layout.spinner_item);
        resultView.setAdapter(resultAdapter);
        resultView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHandler dbHandler = DBHandler.getDBHandler(Settings.this);
                dbHandler.updateViewType(resultViews[position].split(" ")[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (DBHandler.getDBHandler(this).getViewType().equals("Pie")) {
            resultView.setSelection(0);
        } else if (DBHandler.getDBHandler(this).getViewType().equals("Histogram")) {
            resultView.setSelection(1);
        }

        radioGroup = findViewById(R.id.settingsRadioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
        });
    }
}