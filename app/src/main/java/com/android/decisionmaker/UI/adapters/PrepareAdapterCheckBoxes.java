package com.android.decisionmaker.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Criteria;

import java.util.ArrayList;

public class PrepareAdapterCheckBoxes extends RecyclerView.Adapter<PrepareAdapterCheckBoxes.ViewHolder> {

    private final ArrayList<Criteria> arrayList;
    PrepareAdapter adapter;
    ArrayList<Criteria> criteria;
    RecyclerView recyclerView;

    public PrepareAdapterCheckBoxes (ArrayList<Criteria> list, PrepareAdapter adapter,
                                     ArrayList<Criteria> criteria, RecyclerView recyclerView) {
        arrayList = list;
        this.adapter = adapter;
        this.criteria = criteria;
        this.recyclerView = recyclerView;
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
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.prepareCheckBox);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) {
                    Criteria temp = new Criteria();
                    temp.setName(checkBox.getText().toString());
                    criteria.add(temp);
                    adapter.notifyDataSetChanged();
                } else {
                    for(int i=0;i<criteria.size();i++) {
                        if(criteria.get(i).getName().equals(checkBox.getText().toString())) {
                            criteria.remove(i);
                            recyclerView.removeViewAt(criteria.size() - i);
                            adapter.notifyItemRemoved(criteria.size() - i);
                            adapter.notifyItemRangeChanged(criteria.size() - i, criteria.size());
                        }
                    }
                }
            });
        }
    }
}
