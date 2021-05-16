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
    ArrayList<String> decisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        Decision decision = dbHandler.getDecision("First Decision");

        Log.d("Dec Name", decision.getName());
        Log.d("Dec Date", decision.getDate().toString());
        Log.d("Dec SubCat", decision.getSubCategory());

        for (Criteria criteria : decision.getCriteria()) {
            Log.d("Dec Crit Name", criteria.getName());
            Log.d("Dec Crit Weight", String.valueOf(criteria.getWeight()));

            for (Choice choice : criteria.getChoices()) {
                Log.d("Dec Crit Choice Name", choice.getName());
                Log.d("Dec Crit Choice Val", String.valueOf(choice.getValue()));
            }
        }

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