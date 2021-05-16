package com.android.decisionmaker.UI.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;

import java.util.ArrayList;

public class CriteriaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final ArrayList<String> arrayList;

    public CriteriaAdapter (ArrayList<String> list) {
        arrayList = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.criteria_recycler_title, parent, false);

            return new ViewHolder1(v);
        } else if (viewType == arrayList.size()-1) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.criteria_recycler_button, parent, false);
            return new ViewHolder2(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.criteria_recycler_layout, parent, false);
            return new ViewHolder0(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            ViewHolder1 viewHolder1 = (ViewHolder1) holder;
            viewHolder1.name.setText(arrayList.get(position));
        } else if (position == arrayList.size()-1) {
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            viewHolder2.name.setText(arrayList.get(position));
        } else {
            ViewHolder0 viewHolder0 = (ViewHolder0) holder;
            viewHolder0.name.setText(arrayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder0 extends RecyclerView.ViewHolder {

        TextView name;
        SeekBar seekBar;

        public ViewHolder0(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.criteriaProductName);
            seekBar = itemView.findViewById(R.id.criteriaSeekBar);
            seekBar.setMax(1000);

        }
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.criteriaTitle);

        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        Button name;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.criteriaNextButton);

        }
    }

}