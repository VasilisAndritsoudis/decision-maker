package com.example.decisionmaker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final ArrayList<String> arrayList;

    public HistoryAdapter (ArrayList<String> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_recycler_layout, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameAndDate.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameAndDate;
        ImageButton delete;
        ImageButton edit;

        public ViewHolder(View itemView) {
            super(itemView);

            nameAndDate = itemView.findViewById(R.id.historyDecisionText);
            delete = itemView.findViewById(R.id.historyDeleteButton);
            edit = itemView.findViewById(R.id.historyEditButton);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(itemView.getContext(), DecisionView.class);
                String string = nameAndDate.getText().toString();
                i.putExtra("textView" , string);
                itemView.getContext().startActivity(i);
            });

        }
    }
}
