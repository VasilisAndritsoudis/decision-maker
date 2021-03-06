package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.CriteriaAdapter;
import com.android.decisionmaker.UI.adapters.CriteriaAdapterInterface;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Category;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;
import com.android.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;
import java.util.Objects;

public class CriteriaScore extends AppCompatActivity implements CriteriaAdapterInterface {

    RecyclerView recyclerView;
    ArrayList<String> criteria;
    Decision decision;
    CriteriaAdapter adapter;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criteria_score);

        //Adds home button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        extras = getIntent().getExtras();

        //Checks for rotation and saved data
        if(savedInstanceState != null) {
            decision = (Decision) savedInstanceState.get("Decision");
        } else {
            decision = (Decision) extras.get("Decision");
        }

        //creates the appropriate ArrayList for the recycler view adapter based on which iteration is
        if(extras != null) {

            int num = (int) extras.get("Times");

            criteria = new ArrayList<>();
            criteria.add(decision.getCriteria().get(decision.getCriteria().size()-num).getName());

            for(Choice choice : decision.getCriteria().get(0).getChoices()) {
                criteria.add(choice.getName());
            }

            if(num == 1) {
                criteria.add("Ready!");
            } else {
                criteria.add("Next");
            }

            recyclerView = findViewById(R.id.criteriaRecyclerView);
            adapter = new CriteriaAdapter(criteria, decision,
                    decision.getCriteria().size() - num,  this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    /**
     * Decides which function to call to create the next activity
     * @param view is the View Object we are current on
     */
    public void nextActivity (View view) {
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

    /**
     * Calls itself until every criterion has been graded
     * @param num is the number of iterations that have left to be done
     * @param decision is the current Decision Object
     */
    public void recurse(int num, Decision decision) {
        Intent intent = new Intent(this, CriteriaScore.class);
        intent.putExtra("Times" , num);
        intent.putExtra("Decision", decision);

        if (extras != null) {
            if (extras.containsKey("Category")) {
                intent.putExtra("Category", extras.getString("Category"));
            }
            if (extras.containsKey("Edit")) {
                intent.putExtra("Edit", extras.getBoolean("Edit"));
            }
        }

        startActivity(intent);
    }

    /**
     * Saves the Decision Object in the DbHandler and then sends us to the next activity to see
     * the results
     * @param decision is the current Decision Object
     */
    public void goToDecisionView (Decision decision){
        Intent intent = new Intent(this, DecisionView.class);
        intent.putExtra("Decision", decision);

        DBHandler dbHandler = DBHandler.getDBHandler(this);

        if (extras != null) {
            if (extras.containsKey("Category") && extras.getString("Category") != null) {
                Log.d("Save Cat", "Score Criteria Contains Category");
                Category category = new Category();
                category.setName(extras.getString("Category"));
                Log.d("Save Category", String.valueOf(dbHandler.saveCategory(category)));

                SubCategory subCategory = new SubCategory();
                subCategory.setName(decision.getSubCategory());
                subCategory.setCategory(extras.getString("Category"));
                Log.d("Save SubCategory", String.valueOf(dbHandler.saveSubCategory(subCategory,
                        decision.getCriteria())));
            }
        }

        if (!(extras != null && extras.containsKey("Edit"))) {
             Log.d("Save Decision", Boolean.toString(dbHandler.saveDecision(decision)));
        } else {
            Log.d("Update Decision", Boolean.toString(dbHandler.updateDecision(decision)));
        }

        startActivity(intent);
    }


    @Override
    public void onSeekBarChange(int position, int seekBarValue, String criterionName) {
        //Listener to change the value of each Criterion's weight and Choice
        for (Criteria criterion : decision.getCriteria()) {
            if (criterion.getName().equals(criterionName)) {
                if (position == 0) {
                    criterion.setWeight(seekBarValue + 1);
                } else {
                    criterion.getChoices().get(position - 1).setValue(seekBarValue + 1);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putAll(extras);
        outState.putSerializable("Decision", adapter.getDecision());
        super.onSaveInstanceState(outState);
    }
}