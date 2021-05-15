package com.example.decisionmaker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PermaAdapter extends RecyclerView.Adapter<PermaAdapter.ViewHolder>{

    private final ArrayList<String> arrayList;

    public PermaAdapter (ArrayList<String> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public PermaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perma_recycler_layout, parent, false);

        return new PermaAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PermaAdapter.ViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.permaCategoryName);
        }
    }
}
