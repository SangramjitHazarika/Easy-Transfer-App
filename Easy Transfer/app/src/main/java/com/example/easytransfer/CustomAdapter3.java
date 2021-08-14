package com.example.easytransfer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder>{

    static ArrayList<Transaction_Record> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView sender;
        private final TextView receiver;
        private final TextView datetime;
        private final TextView amount;
        private final TextView result;

        public ViewHolder(View view) {
            super(view);
            sender = view.findViewById(R.id.sender);
            receiver = view.findViewById(R.id.receiver);
            datetime = view.findViewById(R.id.datetime);
            amount = view.findViewById(R.id.amount);
            result = view.findViewById(R.id.result);

        }

    }

    // Step 1: Initialize the dataset of the Adapter.

    public CustomAdapter3(ArrayList<Transaction_Record> dataSet) {
        localDataSet = dataSet;
    }

    // Step 2: Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_transaction_history, viewGroup, false);

        return new ViewHolder(view);
    }

    // Step 3: Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        DBHandler2 handler2 = new DBHandler2(viewHolder.itemView.getContext(), "bankdb", null, 1);
//        ArrayList<Transaction_Record> tr = handler2.getAllTransactions();

        viewHolder.sender.setText(localDataSet.get(position).getSender_name());
        viewHolder.receiver.setText(localDataSet.get(position).getReceiver_name());
        viewHolder.amount.setText("Rs. " + String.valueOf(localDataSet.get(position).getAmount()) + " /-");
        viewHolder.datetime.setText(localDataSet.get(position).getDateTime());

        String result = localDataSet.get(position).getResult();
        if(result.equalsIgnoreCase("Success")) {
            viewHolder.result.setText(result);
            viewHolder.result.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.success));
        } else {
            viewHolder.result.setText(result);
            viewHolder.result.setTextColor(viewHolder.itemView.getContext().getResources().getColor(R.color.errorColor));
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
