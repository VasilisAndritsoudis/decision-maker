package com.android.decisionmaker.UI.adapters;

import android.util.Pair;
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

    private ArrayList<Pair<Criteria, Boolean>> arrayList;
    PrepareAdapter adapter;
    ArrayList<Criteria> criteria;
    RecyclerView recyclerView;

    public PrepareAdapterCheckBoxes (ArrayList<Pair<Criteria, Boolean>> list, PrepareAdapter adapter,
                                     ArrayList<Criteria> criteria, RecyclerView recyclerView) {
        arrayList = list;
        this.adapter = adapter;
        this.criteria = criteria;
        this.recyclerView = recyclerView;
    }

    public ArrayList<Pair<Criteria, Boolean>> getArrayList() {
        return arrayList;
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
        holder.checkBox.setText(arrayList.get(position).first.getName());
        holder.checkBox.setChecked(arrayList.get(position).second);
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

            //a listener for whenever the user checks or unchecks a check box in order to added/remove
            //it from an arraylist and notify the correspondent adapter that uses this arraylist
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(isChecked) {
                    boolean add = true;
                    for(Criteria criterion : criteria) {
                        if(criterion.getName().equals(checkBox.getText().toString()))
                            add = false;
                    }
                    if(add) {
                        Criteria temp = new Criteria();
                        temp.setName(checkBox.getText().toString());
                        criteria.add(temp);
                    }

                    ArrayList<Pair<Criteria,Boolean>> tmp = new ArrayList<>();
                    for(Pair<Criteria,Boolean> pair : arrayList) {
                        if(pair.first.getName().equals(checkBox.getText().toString())) {
                            tmp.add(new Pair<>(pair.first, true));
                        } else {
                            tmp.add(pair);
                        }
                    }
                    arrayList = tmp;

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

                    ArrayList<Pair<Criteria,Boolean>> tmp = new ArrayList<>();
                    for(Pair<Criteria,Boolean> pair : arrayList) {
                        if(pair.first.getName().equals(checkBox.getText().toString())) {
                            tmp.add(new Pair<>(pair.first, false));
                        } else {
                            tmp.add(pair);
                        }
                    }
                    arrayList = tmp;
                }
            });
        }
    }
}
