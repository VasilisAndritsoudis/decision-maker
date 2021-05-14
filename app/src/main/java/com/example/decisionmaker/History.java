package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> decisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        decisions = new ArrayList<>();
        decisions.add("Nokia vs LG\n22:25 - 13/5/2021 ");
        decisions.add("Phones\n12:25 - 13/5/2021 ");
        decisions.add("Going Out\n21:25 - 13/5/2021 ");
        decisions.add("Board Games\n00:00 - 13/5/2021 ");
        decisions.add("Virtual Reality vs Augment Reality\n22:25 - 13/5/2021 ");
        decisions.add("More vs Less\n20:25 - 13/5/2021 ");
        decisions.add("Nokia vs LG\n22:25 - 13/5/2021 ");
        decisions.add("Phones\n12:25 - 13/5/2021 ");
        decisions.add("Going Out\n21:25 - 13/5/2021 ");
        decisions.add("Board Games\n00:00 - 13/5/2021 ");
        decisions.add("Virtual Reality vs Augment Reality\n22:25 - 13/5/2021 ");
        decisions.add("More vs Less\n20:25 - 13/5/2021 ");

        recyclerView = findViewById(R.id.historyRecyclerView);
        HistoryAdapter adapter = new HistoryAdapter(decisions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}