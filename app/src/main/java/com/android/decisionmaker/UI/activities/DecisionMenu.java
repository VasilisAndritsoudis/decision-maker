package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.MenuAdapter;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Category;

import java.util.ArrayList;

public class DecisionMenu extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_menu);

        DBHandler dbHandler = DBHandler.getDBHandler(this);
        categories = dbHandler.getCategories();

        Category plus = new Category();
        plus.setName("+");
        categories.add(plus);
        recyclerView = findViewById(R.id.menuRecyclerView);
        MenuAdapter adapter = new MenuAdapter(categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}