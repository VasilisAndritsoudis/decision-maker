package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.HistoryAdapter;
import com.android.decisionmaker.UI.adapters.HistoryAdapterInterface;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Decision;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class History extends AppCompatActivity implements HistoryAdapterInterface {

    RecyclerView recyclerView;
    ArrayList<Decision> decisions;
    ArrayList<HistoryAdapter.Pair> pairs;

    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHandler dbHandler = DBHandler.getDBHandler(this);

        //Adds Home Button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        decisions = dbHandler.getDecisions();
        pairs = new ArrayList<>();

        //Gets all the past user's decision from the database and prints them
        for (Decision decision : decisions) {
            String pattern = "HH:mm - EEE dd/MM/yy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            String date = simpleDateFormat.format(decision.getDate());

            HistoryAdapter.Pair pair = new HistoryAdapter.Pair(decision, date);
            pairs.add(pair);
        }

        adapter = new HistoryAdapter(pairs, this);
        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickEdit(int position) {
        Decision decision = pairs.get(position).getDecision();

        Intent intent = new Intent(this, CriteriaScore.class);

        intent.putExtra("Times",decision.getCriteria().size());
        intent.putExtra("Decision", decision);
        intent.putExtra("Edit", true);

        startActivity(intent);
    }

    @Override
    public void onClickDelete(int position) {
        DBHandler dbHandler = DBHandler.getDBHandler(this);

        Log.d("Delete", "Button Pressed");

        Log.d("Dec Delete", String.valueOf(dbHandler.deleteDecision(pairs.get(position).getDecision())));

        pairs.remove(position);
        decisions.remove(position);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
    }
}
