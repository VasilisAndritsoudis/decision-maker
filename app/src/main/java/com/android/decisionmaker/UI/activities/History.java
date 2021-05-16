package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.HistoryAdapter;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Decision> decisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        decisions = dbHandler.getDecisions();

        if (decisions == null) {
            decisions = new ArrayList<>();
        }

        recyclerView = findViewById(R.id.historyRecyclerView);
        HistoryAdapter adapter = new HistoryAdapter(decisions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}