package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.decisionmaker.database.handlers.DBHandler;
import com.example.decisionmaker.database.models.Category;
import com.example.decisionmaker.database.models.Criteria;
import com.example.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;

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
    }
}