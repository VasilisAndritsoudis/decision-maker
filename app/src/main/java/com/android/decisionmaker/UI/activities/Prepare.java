    package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PrepareAdapter;
import com.android.decisionmaker.UI.adapters.PrepareAdapterCheckBoxes;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;
import java.util.Objects;

public class Prepare extends AppCompatActivity {


    ArrayList<Criteria> criteria;
    ArrayList<Pair<Criteria,Boolean>> checkBoxCriteria;
    PrepareAdapterCheckBoxes checkBoxAdapter;
    Decision decision;
    EditText criterion;
    PrepareAdapter criteriaAdapter;
    RecyclerView recyclerAddedCriteria;
    RecyclerView checkBoxes;
    TextView warning;
    Bundle extras;
    TextView select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        criterion = findViewById(R.id.prepareEditText);
        select = findViewById(R.id.prepareSelectTv);
        warning = findViewById(R.id.prepareWarning);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        checkBoxCriteria = new ArrayList<>();

        DBHandler dbHandler = DBHandler.getDBHandler(this);
        ArrayList<Criteria> temp = null;
        extras = getIntent().getExtras();
        if (extras != null) {
            decision = (Decision) extras.get("Decision");
            if(decision.getSubCategory() != null){
                temp = dbHandler.getSubCategoryCriteria(decision.getSubCategory());
                if(temp == null) {
                    if(extras.containsKey("Category") && extras.getString("Category") != null){
                        temp = dbHandler.getCategoryCriteria(extras.getString("Category"));
                    } else {
                        select.setVisibility(View.INVISIBLE);
                    }
                }
            } else {
                checkBoxCriteria = null;
                select.setVisibility(View.INVISIBLE);
            }
        }


        if(savedInstanceState != null) {
            criteria = (ArrayList<Criteria>) savedInstanceState.get("AddedCriteria");
            criterion.setText(savedInstanceState.getString("Criterion"));
            if(savedInstanceState.containsKey("Warning")) {
                warning.setText(savedInstanceState.getString("Warning"));
                warning.setVisibility(savedInstanceState.getInt("Visibility"));
            }
            if (temp != null)
                for(Criteria criteria1 : temp) {
                    boolean added = false;
                    for (Criteria criteria2 : criteria){
                        if (criteria2.getName().equals(criteria1.getName())){
                            checkBoxCriteria.add(new Pair<>(criteria1,true));
                            added = true;
                            break;
                        }
                    }
                    if (!added)
                        checkBoxCriteria.add(new Pair<>(criteria1,false));
                }
        } else {
            criteria = new ArrayList<>();
            if (temp != null)
                for(Criteria criteria : temp) {
                    checkBoxCriteria.add(new Pair<>(criteria,false));
                }
        }

        recyclerAddedCriteria = findViewById(R.id.prepareRecyclerCriteria);
        criteriaAdapter = new PrepareAdapter(criteria);
        recyclerAddedCriteria.setAdapter(criteriaAdapter);
        recyclerAddedCriteria.setLayoutManager(new LinearLayoutManager(this));

        checkBoxes = findViewById(R.id.prepareRecyclerCheckBoxes);
        checkBoxAdapter = new PrepareAdapterCheckBoxes(checkBoxCriteria,
                criteriaAdapter, criteria, recyclerAddedCriteria);
        checkBoxes.setAdapter(checkBoxAdapter);
        checkBoxes.setLayoutManager(new LinearLayoutManager(this));

        criterion.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCriterion(v);
                return true;
            }
            return false;
        });

    }

    public void addCriterion(View view) {
        if(criterion.getText().toString().isEmpty())
            return;
        for(Criteria item : criteria)
            if(item.getName().equalsIgnoreCase(criterion.getText().toString())) {
                criterion.setText("");
                return;
            }
        Criteria temp = new Criteria();
        temp.setName(criterion.getText().toString().trim());
        criteria.add(temp);
        criterion.setText("");
        criteriaAdapter.notifyDataSetChanged();
    }

    public void goToCriteriaScoring(View view) {
        if(criteria.size()<=0) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("You have to select or add at least\none criterion to proceed!");
            return;
        }
        if(extras==null)
            return;
        ArrayList<Choice> choices;
        choices = (ArrayList<Choice>) extras.get("Choices");

        for (Criteria criterion : criteria) {
            ArrayList<Choice> newChoices = new ArrayList<>();
            for (Choice choice : choices) {
                Choice temp = new Choice();
                temp.setId(choice.getId());
                temp.setName(choice.getName());
                temp.setValue(choice.getValue());
                newChoices.add(temp);
            }
            criterion.setChoices(newChoices);
        }

        decision.setCriteria(criteria);
        Intent intent = new Intent(this, CriteriaScore.class);

        for (Criteria criteria : decision.getCriteria()) {
            for (Choice choice : criteria.getChoices()) {
                choice.setValue(50);
            }
            criteria.setWeight(50);
        }

        intent.putExtra("Times",decision.getCriteria().size());
        intent.putExtra("Decision", decision);

        if (extras.containsKey("Category")) {
            intent.putExtra("Category", extras.getString("Category"));
        }

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putAll(extras);
        outState.putString("Criterion", criterion.getText().toString());
        outState.putSerializable("AddedCriteria", criteria);
        if(!warning.getText().toString().isEmpty()){
            outState.putString("Warning", warning.getText().toString());
            outState.putInt("Visibility", warning.getVisibility());
        }
        super.onSaveInstanceState(outState);
    }

}