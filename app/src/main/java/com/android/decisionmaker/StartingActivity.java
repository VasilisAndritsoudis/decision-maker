package com.android.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Category;
import com.android.decisionmaker.database.models.Choice;
import com.android.decisionmaker.database.models.Criteria;
import com.android.decisionmaker.database.models.Decision;
import com.android.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;
import java.util.Date;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_activity);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void goToHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);

        // Testing the database
        // Please ignore

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<SubCategory> subCatResponse = dbHandler.getSubCategoriesOfCategory("Shopping");

        for (SubCategory item : subCatResponse) {
            Log.d("SubCats for Shopping", item.getName());
        }

        ArrayList<Category> catResponse = dbHandler.getCategories();

        for (Category item : catResponse) {
            Log.d("Categories", item.getName());
        }

        ArrayList<Criteria> subCatCriteriaResponse = dbHandler.getSubCategoryCriteria("Phones");

        for (Criteria item : subCatCriteriaResponse) {
            Log.d("Criteria for phones", item.getName());
        }

        ArrayList<Criteria> catCriteriaResponse = dbHandler.getCategoryCriteria("Shopping");

        for (Criteria item : catCriteriaResponse) {
            Log.d("Criteria for shopping", item.getName());
        }
    }

    public void goToDecisionMenu(View view) {
        Intent intent = new Intent(this, DecisionMenu.class);
        startActivity(intent);

        // Testing the database
        // Please ignore

        Decision decision = new Decision();

        decision.setName("First Decision");
        decision.setDate(new Date());
        decision.setSubCategory("Phones");

        ArrayList<Criteria> criteria = new ArrayList<>();

        Criteria criteria1 = new Criteria();
        criteria1.setName("Price");
        criteria1.setWeight(8);

        Criteria criteria2 = new Criteria();
        criteria2.setName("RAM");
        criteria2.setWeight(6);

        ArrayList<Choice> choices = new ArrayList<>();

        Choice choice = new Choice();
        choice.setName("Samsung");
        choice.setValue(7);

        Choice choice1 = new Choice();
        choice1.setName("Iphone");
        choice1.setValue(8);

        choices.add(choice);
        choices.add(choice1);

        criteria1.setChoices(choices);
        criteria2.setChoices(choices);

        criteria.add(criteria1);
        criteria.add(criteria2);

        decision.setCriteria(criteria);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        Log.d("Save Decision", Boolean.toString(dbHandler.saveDecision(decision)));
    }
}