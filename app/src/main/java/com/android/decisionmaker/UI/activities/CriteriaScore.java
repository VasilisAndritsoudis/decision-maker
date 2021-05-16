package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.CriteriaAdapter;

import java.util.ArrayList;

public class CriteriaScore extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> products;
    ArrayList<String> criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criteria_score);

        products = new ArrayList<>();

        criteria = new ArrayList<>();

        ArrayList<String> temp = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if(extras==null) {
            temp.add(criteria.get(0));
        } else if ((int) extras.get("Times") != 0){
            temp.add(criteria.get(criteria.size() - (int) extras.get("Times")));
        }
        temp.addAll(products);

        recyclerView = findViewById(R.id.criteriaRecyclerView);
        CriteriaAdapter adapter = new CriteriaAdapter(temp);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void nextActivity (View view) {
        Bundle extras = getIntent().getExtras();
        int num;

//        Decision decision = (Decision) extras.get("Decision");
//        ArrayList<Choice> choices;
//
//        for (Criteria criteria : decision.getCriteria()) {
//            if (criteria.getName().equals(this.criteria.get(this.criteria.size() - num))) {
//                criteria.setWeight(1);
//                choices = criteria.getChoices();
//                break;
//            }
//        }

        // TODO Set value for each choice.

        if(extras == null) {
            recurse(criteria.size()-1);
        } else {
            num = (int) extras.get("Times");
            if (num == 1) {
                goToDecisionView(view);
            } else {
                recurse(num-1);
            }
        }
    }


    public void recurse(int num) {
        Intent intent = new Intent(this, CriteriaScore.class);
        intent.putExtra("Times" , num);
        startActivity(intent);
    }

    public void goToDecisionView (View view){
        Intent intent = new Intent(this, DecisionView.class);
        startActivity(intent);
    }
}