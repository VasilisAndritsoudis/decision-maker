package com.android.decisionmaker.UI.adapters;

import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.UI.activities.AddChoices;
import com.android.decisionmaker.UI.activities.Perma;
import com.android.decisionmaker.database.models.SubCategory;

import java.util.ArrayList;

public class SubMenuAdapter extends RecyclerView.Adapter<SubMenuAdapter.ViewHolder>{

    private final ArrayList<SubCategory> arrayList;
    static String category;

    public SubMenuAdapter (ArrayList<SubCategory> list, String category) {
        arrayList = list;
        SubMenuAdapter.category = category;
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
        if(position == arrayList.size()-1) {
            holder.button.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
        }
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
                if(button.getText().toString().equals("+")) {
                    Intent i = new Intent(itemView.getContext(), Perma.class);
                    i.putExtra("Perma", category);
                    itemView.getContext().startActivity(i);
                } else {
                    Intent i = new Intent(itemView.getContext(), AddChoices.class);
                    String string = button.getText().toString();
                    i.putExtra("SubCategory", string);
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
