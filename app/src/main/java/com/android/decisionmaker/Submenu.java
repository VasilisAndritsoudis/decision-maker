package com.android.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Submenu extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> decisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        decisions = new ArrayList<>();


        Bundle extras = getIntent().getExtras();
        String name;

        if (extras != null) {
            name = extras.getString("buttonPressed");
            if (name.equals("Shopping")) {
                decisions.add("New Subcategory");
                decisions.add("Smartphones");
                decisions.add("Cars");
                decisions.add("Houses");
                decisions.add("Televisions");
            } else {
                decisions.add("Personal Computers");
                decisions.add("Turing Machines");
                decisions.add("Raspberry Pies");
                decisions.add("Video Games");
            }
        }

        recyclerView = findViewById(R.id.submenuRecyclerView);
        MenuAdapter adapter = new MenuAdapter(decisions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}