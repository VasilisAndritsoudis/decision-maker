package com.android.decisionmaker.UI.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.decisionmaker.R;

import java.util.ArrayList;

public class DataTextsAdapter extends RecyclerView.Adapter<DataTextsAdapter.ViewHolder>{

    private final ArrayList<String> arrayList;

    /**
     * A getter
     * @return the arraylist in each current state
     */
    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    /**
     * Adapter's constructor
     * @param list is the Arraylist of Strings that have to been printed
     */
    public DataTextsAdapter (ArrayList<String> list) {
        arrayList = list;
    }

    @NonNull
    @Override
    public DataTextsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_text_recycler, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DataTextsAdapter.ViewHolder holder, int position) {
        String [] strings = arrayList.get(position).split("~");
        holder.name.setText(strings[0] + ":");
        holder.value.setText(strings[1]);
    }

    @Override
    public int getItemCount() {
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dataViewName);
            value = itemView.findViewById(R.id.dataViewValue);
        }
    }
}
