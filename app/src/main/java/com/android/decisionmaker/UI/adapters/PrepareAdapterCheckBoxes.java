package com.android.decisionmaker.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Criteria;

import java.util.ArrayList;

public class PrepareAdapterCheckBoxes extends RecyclerView.Adapter<PrepareAdapterCheckBoxes.ViewHolder> {

    private final ArrayList<Criteria> arrayList;

    public PrepareAdapterCheckBoxes (ArrayList<Criteria> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prepare_checkboxes_layout, parent, false);

        return new PrepareAdapterCheckBoxes.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkBox.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.prepareCheckBox);
        }
    }
}
