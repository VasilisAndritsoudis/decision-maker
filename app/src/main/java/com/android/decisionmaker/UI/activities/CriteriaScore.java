package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.CriteriaAdapter;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;
import java.util.Objects;

public class CriteriaScore extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> criteria;
    Decision decision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criteria_score);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            decision = (Decision) extras.get("Decision");
            int num = (int) extras.get("Times");

            criteria = new ArrayList<>();
            criteria.add(decision.getCriteria().get(decision.getCriteria().size()-num).getName());
            criteria.add("Weight");
            for(Choice choice : decision.getCriteria().get(0).getChoices()) {
                criteria.add(choice.getName());
            }
            if(num == 1) {
                criteria.add("Ready!");
            } else {
                criteria.add("Next");
            }

            recyclerView = findViewById(R.id.criteriaRecyclerView);
            CriteriaAdapter adapter = new CriteriaAdapter(criteria,decision,decision.getCriteria().size()-num);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void nextActivity (View view) {
        Bundle extras = getIntent().getExtras();
        int num;

        if(extras == null)
            return;
        num = (int) extras.get("Times");
        if (num == 1) {
            goToDecisionView(decision);
        } else {
            recurse(num - 1, decision);
        }

    }


    public void recurse(int num, Decision decision) {
        Intent intent = new Intent(this, CriteriaScore.class);
        intent.putExtra("Times" , num);
        intent.putExtra("Decision", decision);
        startActivity(intent);
    }

    public void goToDecisionView (Decision decision){
        Intent intent = new Intent(this, DecisionView.class);
        intent.putExtra("Decision", decision);

        Log.d("Dec Save Debug", String.valueOf(decision.getCriteria().get(0).getChoices().size()));

        DBHandler dbHandler = DBHandler.getDBHandler(this);
        Log.d("Save Decision", Boolean.toString(dbHandler.saveDecision(decision)));

        startActivity(intent);
    }
}