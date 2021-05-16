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
import com.android.decisionmaker.UI.activities.Prepare;
import com.android.decisionmaker.UI.activities.Submenu;
import com.android.decisionmaker.database.models.Category;
import com.android.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;

public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.ViewHolder>{

    private final ArrayList<SubCategory> arrayList;

    public SubMenuAdapter (ArrayList<SubCategory> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public SubMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_recycler_layout, parent, false);
        return new SubMenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubMenuAdapter.ViewHolder holder, int position) {
        holder.button.setText(arrayList.get(position).getName());
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
                    Intent i = new Intent(itemView.getContext(), Prepare.class);
                    String string = button.getText().toString();
                    i.putExtra("buttonPressed", string);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
