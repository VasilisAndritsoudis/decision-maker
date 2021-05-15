package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Perma extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma);

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


        recyclerView = findViewById(R.id.permaRecyclerView);
        PermaAdapter adapter = new PermaAdapter(categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}