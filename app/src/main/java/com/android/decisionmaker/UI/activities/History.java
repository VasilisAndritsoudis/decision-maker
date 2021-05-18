package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.HistoryAdapter;
import com.android.decisionmaker.UI.adapters.HistoryAdapterInterface;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        decisions = dbHandler.getDecisions();
        pairs = new ArrayList<>();

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
    public void onClick(int position) {
        DBHandler dbHandler = DBHandler.getDBHandler(this);

        Log.d("Dec Delete", String.valueOf(dbHandler.deleteDecision(pairs.get(position).getDecision())));

        pairs.remove(position);
        decisions.remove(position);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
    }
}
