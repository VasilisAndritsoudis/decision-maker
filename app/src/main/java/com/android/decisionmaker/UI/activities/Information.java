package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.decisionmaker.R;

import java.util.Objects;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information);

        //Enable the UP button on the app bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}