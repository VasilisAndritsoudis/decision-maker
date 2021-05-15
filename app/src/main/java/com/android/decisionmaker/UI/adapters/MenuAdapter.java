package com.android.decisionmaker.UI.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.UI.activities.Perma;
import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.activities.Submenu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    private final ArrayList<String> arrayList;
    static ArrayList<String> categories;
    static ArrayList<String> decisions;

    public MenuAdapter (ArrayList<String> list) {
        arrayList = list;

        //This is gonna be deleted, is only for practise
        categories = new ArrayList<>();
        categories.add("New Category");
        categories.add("Shopping");
        categories.add("Activities");
        categories.add("Movies");
        categories.add("Theater");
        categories.add("Drinks");
        categories.add("New Category");
        categories.add("Shopping");
        categories.add("Activities");
        categories.add("Movies");
        categories.add("Theater");
        categories.add("Drinks");

        decisions = new ArrayList<>();
        decisions.add("New Subcategory");
        decisions.add("Smartphones");
        decisions.add("Cars");
        decisions.add("Houses");
        decisions.add("Televisions");
        decisions.add("Personal Computers");
        decisions.add("Turing Machines");
        decisions.add("Raspberry Pies");
        decisions.add("Video Games");
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_recycler_layout, parent, false);
        return new MenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        holder.button.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.menuButton);
            button.setOnClickListener(v -> {
                if(button.getText().toString().contains("New")) {

                    Intent i = new Intent(itemView.getContext(), Perma.class);
                    String string = button.getText().toString();
                    i.putExtra("buttonPressed", string);
                    itemView.getContext().startActivity(i);

                } else {
                    Intent i = new Intent(itemView.getContext(), Submenu.class);
                    String string = button.getText().toString();
                    i.putExtra("buttonPressed", string);
                    itemView.getContext().startActivity(i);
                }
            });

        }
    }
}
