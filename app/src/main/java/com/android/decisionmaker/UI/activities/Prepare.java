package com.android.decisionmaker.UI.activities;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
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
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Prepare extends AppCompatActivity {

    ArrayList<Choice> choices;
    ArrayList<Criteria> criteria;
    ArrayList<Criteria> checkBoxCriteria;

    String subCategoryName;

    EditText choice;
    EditText criterion;

    RecyclerView recyclerAddedChoices;
    RecyclerView recyclerAddedCriteria;
    RecyclerView checkBoxes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subCategoryName = extras.getString("buttonPressed");
            checkBoxCriteria = dbHandler.getSubCategoryCriteria(subCategoryName);
        }

        recyclerAddedChoices = findViewById(R.id.prepareRecyclerChoices);
        PrepareAdapterAdded choicesAdapter = new PrepareAdapterAdded(null);
        recyclerAddedChoices.setAdapter(choicesAdapter);
        recyclerAddedChoices.setLayoutManager(new LinearLayoutManager(this));

        recyclerAddedCriteria = findViewById(R.id.prepareRecyclerCriteria);
        PrepareAdapterAdded criteriaAdapter = new PrepareAdapterAdded(null);
        recyclerAddedCriteria.setAdapter(criteriaAdapter);
        recyclerAddedCriteria.setLayoutManager(new LinearLayoutManager(this));

        checkBoxes = findViewById(R.id.prepareRecyclerCheckBoxes);
        PrepareAdapterCheckBoxes checkBoxAdapter = new PrepareAdapterCheckBoxes(checkBoxCriteria);
        checkBoxes.setAdapter(checkBoxAdapter);
        checkBoxes.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goToCriteriaScoring(View view) {
        // -----------------------------------------
        // In first part (Choices)
        ArrayList<Choice> choices; // Pass to next activity

        Decision decision = new Decision(); // Pass to next activity

        // TODO add decision name text box
        decision.setName("From text box");
        decision.setDate(new Date());
        decision.setSubCategory(subCategoryName);
        // -----------------------------------------

        for (Criteria item : criteria) {
            item.setChoices(choices);
        }

        decision.setCriteria(criteria);
    }

    // On every Choice/Criteria addition, add the Choice/Criteria (object) to its respective array list

}