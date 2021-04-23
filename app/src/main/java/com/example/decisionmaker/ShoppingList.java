package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShoppingList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
    }

    public void goToSmartphoneCriteria(View view){
        Intent intent = new Intent(this, SmartphoneCriteria.class);
        startActivity(intent);
    }
}