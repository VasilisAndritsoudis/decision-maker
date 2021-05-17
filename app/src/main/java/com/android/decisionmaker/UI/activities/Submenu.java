package com.android.decisionmaker.UI.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.SubMenuAdapter;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;

public class Submenu extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SubCategory> subCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        Bundle extras = getIntent().getExtras();
        String name;

        if (extras != null) {
            name = extras.getString("Category");
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            subCategories = dbHandler.getSubCategoriesOfCategory(name);
            SubCategory plus = new SubCategory();
            plus.setName("+");
            subCategories.add(plus);

            recyclerView = findViewById(R.id.submenuRecyclerView);
            SubMenuAdapter adapter = new SubMenuAdapter(subCategories, name);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }
}