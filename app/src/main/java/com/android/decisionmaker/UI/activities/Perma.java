package com.android.decisionmaker.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.adapters.PermaAdapter;
import com.android.decisionmaker.database.handlers.DBHandler;
import com.android.decisionmaker.database.models.Category;
import com.android.decisionmaker.database.models.Decision;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Perma extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Pair<Category,Boolean>> categories;

    TextView categoryNameTv;
    TextView subCategoryNameTv;
    EditText categoryName;
    EditText subCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perma);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        DBHandler dbHandler = DBHandler.getDBHandler(this);
        ArrayList<Category> temp = dbHandler.getCategories();

        categoryName =findViewById(R.id.permaCategoryEditext);
        categoryNameTv = findViewById(R.id.permaCategoryLabel);
        subCategoryNameTv = findViewById(R.id.permaSubCategoryLabel);
        subCategoryName = findViewById(R.id.permaSubCategoryEditText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            categories = new ArrayList<>();
            boolean enable;
            String string = extras.getString("Perma");
            if (!string.equals("None")) {
                enable = false;
                categoryName.setText(string);
                subCategoryName.setVisibility(View.VISIBLE);
                subCategoryNameTv.setVisibility(View.VISIBLE);
                for(Category category : temp) {
                    if(category.getName().equals(string)){
                        categories.add(new Pair<>(category, true));
                    } else {
                        categories.add(new Pair<>(category,false));
                    }
                }

            } else {
                enable = true;
                for(Category category : temp) {
                    categories.add(new Pair<>(category,false));
                }
            }
            Category category = new Category();
            category.setName("New Category");
            categories.add(new Pair<>(category,false));
            recyclerView = findViewById(R.id.permaRecyclerView);
            PermaAdapter adapter = new PermaAdapter(categories, enable,categoryName,categoryNameTv);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }


    }

    public void goToAddChoices(View view) {
        Intent intent = new Intent(this, AddChoices.class);
        if(categoryName.getText().toString().isEmpty())
            return;
        if (subCategoryName.getText().toString().isEmpty())
            return;
        if(categoryName.getText().toString().equals("New Category"))
            return;
        intent.putExtra("SubCategory",subCategoryName.getText().toString());
        intent.putExtra("Category", categoryName.getText().toString());
        startActivity(intent);
    }
}