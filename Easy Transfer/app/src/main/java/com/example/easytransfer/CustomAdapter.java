package com.example.easytransfer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    static ArrayList<User> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView balance;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.textView2);
            balance = (TextView) view.findViewById(R.id.balance);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    int position = getAdapterPosition();
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("sender_email", localDataSet.get(position).getEmail_id());
                    ed.apply();
                    v.getContext().startActivity(new Intent(v.getContext(), User_Info.class));
                }
            });
        }

    }

    // Step 1: Initialize the dataset of the Adapter.

    public CustomAdapter(ArrayList<User> dataSet) {
        localDataSet = dataSet;
    }

    // Step 2: Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Step 3: Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.setText(localDataSet.get(position).getName());
        viewHolder.balance.setText("Rs. " + String.valueOf(localDataSet.get(position).getBalance()) + " /-");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
