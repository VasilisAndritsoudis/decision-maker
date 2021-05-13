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
        decisions.add("Nokia vs LG - 22:25 - 13/5/2021 ");
        decisions.add("Phones - 12:25 - 13/5/2021 ");
        decisions.add("Going Out - 21:25 - 13/5/2021 ");
        decisions.add("Board Games - 00:00 - 13/5/2021 ");
        decisions.add("Virtual Reality vs Augment Reality - 22:25 - 13/5/2021 ");
        decisions.add("More vs Less - 20:25 - 13/5/2021 ");

        recyclerView = findViewById(R.id.historyRecyclerView);
        HistoryAdapter adapter = new HistoryAdapter(decisions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}