package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.AddChoicesAdapter;
import com.android.decisionmaker.UI.adapters.PrepareAdapter;
import com.android.decisionmaker.UI.adapters.PrepareAdapterCheckBoxes;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Prepare extends AppCompatActivity {


    ArrayList<Criteria> criteria;
    ArrayList<Criteria> checkBoxCriteria;
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


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        DBHandler dbHandler = DBHandler.getDBHandler(this);

        extras = getIntent().getExtras();
        if (extras != null) {
            decision = (Decision) extras.get("Decision");
            checkBoxCriteria = dbHandler.getSubCategoryCriteria(decision.getSubCategory());
            if(checkBoxCriteria == null) {
                checkBoxCriteria = dbHandler.getCategoryCriteria(extras.getString("Category"));
                if (checkBoxCriteria == null)
                    select.setVisibility(View.INVISIBLE);
            }

        }

        criteria = new ArrayList<>();

        recyclerAddedCriteria = findViewById(R.id.prepareRecyclerCriteria);
        criteriaAdapter = new PrepareAdapter(criteria);
        recyclerAddedCriteria.setAdapter(criteriaAdapter);
        recyclerAddedCriteria.setLayoutManager(new LinearLayoutManager(this));

        checkBoxes = findViewById(R.id.prepareRecyclerCheckBoxes);
        PrepareAdapterCheckBoxes checkBoxAdapter = new PrepareAdapterCheckBoxes(checkBoxCriteria,
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

        warning = findViewById(R.id.prepareWarning);
    }

    public void addCriterion(View view) {
        if(criterion.getText().toString().isEmpty())
            return;
        for(Criteria item : criteria)
            if(item.getName().equals(criterion.getText().toString())) {
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
        if(criteria.size()<=1) {
            warning.setVisibility(View.VISIBLE);
            warning.setText("You have to select or add at least\ntwo criteria to proceed!");
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
                choice.setValue(1);
            }
        }


        intent.putExtra("Times",decision.getCriteria().size());
        intent.putExtra("Decision", decision);

        if (extras.containsKey("Category")) {
            intent.putExtra("Category", extras.getString("Category"));
        }

        startActivity(intent);
    }

    // On every Choice/Criteria addition, add the Choice/Criteria (object) to its respective array list

}