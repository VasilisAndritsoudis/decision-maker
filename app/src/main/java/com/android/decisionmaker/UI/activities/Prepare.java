package com.android.decisionmaker.UI.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PrepareAdapterAdded;
import com.android.decisionmaker.UI.adapters.PrepareAdapterCheckBoxes;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;

public class Prepare extends AppCompatActivity {


    ArrayList<Criteria> criteria;
    ArrayList<Criteria> checkBoxCriteria;
    Decision decision;
    EditText criterion;
    PrepareAdapterAdded criteriaAdapter;
    RecyclerView recyclerAddedCriteria;
    RecyclerView checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            decision = (Decision) extras.get("Decision");
            checkBoxCriteria = dbHandler.getSubCategoryCriteria(decision.getSubCategory());
        }


        recyclerAddedCriteria = findViewById(R.id.prepareRecyclerCriteria);
        criteriaAdapter = new PrepareAdapterAdded(null);
        recyclerAddedCriteria.setAdapter(criteriaAdapter);
        recyclerAddedCriteria.setLayoutManager(new LinearLayoutManager(this));

        checkBoxes = findViewById(R.id.prepareRecyclerCheckBoxes);
        PrepareAdapterCheckBoxes checkBoxAdapter = new PrepareAdapterCheckBoxes(checkBoxCriteria);
        checkBoxes.setAdapter(checkBoxAdapter);
        checkBoxes.setLayoutManager(new LinearLayoutManager(this));

    }

    public void addCriterion(View view) {
        if(criterion.getText().toString().isEmpty())
            return;
        Criteria temp = new Criteria();
        temp.setName(criterion.getText().toString().trim());
        criteria.add(temp);
        criterion.setText("");
        criteriaAdapter.notifyDataSetChanged();
    }

    public void goToCriteriaScoring(View view) {

        // TODO add decision name text box


    //    for (Criteria item : criteria) {
  //          item.setChoices(choices);
//        }

        decision.setCriteria(criteria);
    }

    // On every Choice/Criteria addition, add the Choice/Criteria (object) to its respective array list

}