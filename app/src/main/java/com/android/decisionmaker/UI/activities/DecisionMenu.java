package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.MenuAdapter;

import java.util.ArrayList;

public class DecisionMenu extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_menu);

        categories = new ArrayList<>();
        categories.add("New Category");
        categories.add("Shopping");
        categories.add("Activities");
        categories.add("Movies");
        categories.add("Theater");
        categories.add("Drinks");
        categories.add("New Category");
        categories.add("Shopping");
        categories.add("Activities");
        categories.add("Movies");
        categories.add("Theater");
        categories.add("Drinks");

        recyclerView = findViewById(R.id.menuRecyclerView);
        MenuAdapter adapter = new MenuAdapter(categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}