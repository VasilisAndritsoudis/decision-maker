package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DecisionView extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_view);

        Bundle extras = getIntent().getExtras();
        String nameAndDate;
        name = findViewById(R.id.ViewTitle);

        if (extras != null) {
            nameAndDate = extras.getString("textView");
            name.setText(nameAndDate);
        }
    }
}