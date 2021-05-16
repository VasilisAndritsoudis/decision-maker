package com.android.decisionmaker.UI.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.UI.activities.DecisionView;
import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Decision;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final ArrayList<Decision> arrayList;

    public HistoryAdapter (ArrayList<Decision> list) {
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
        String pattern = "HH:mm - EEE dd/MM/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        String date = simpleDateFormat.format(arrayList.get(position).getDate());

        String text = arrayList.get(position).getName() + "\n" + date;
        holder.nameAndDate.setText(text);
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
