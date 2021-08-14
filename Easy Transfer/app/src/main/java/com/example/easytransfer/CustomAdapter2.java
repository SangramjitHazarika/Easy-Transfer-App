package com.example.easytransfer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder>{

    static ArrayList<User> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView balance;
        private TextView sender_name_tv;
        private TextView receiver_name_tv;
        private TextView amount_money_tv;


        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name_2);
            balance = (TextView) view.findViewById(R.id.balance2);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("receiver_email", localDataSet.get(getAdapterPosition()).getEmail_id());
                    ed.putString("result", "Success");
                    ed.apply();

                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_final_details, null, false);

                    String sender_email = sp.getString("sender_email","");

                    String receiver_email = sp.getString("receiver_email","");
                    String receiver_name = "Not Selected";

                    int amount = sp.getInt("amount",0);

                    DBHandler handler1 = new DBHandler(v.getContext(), "bankdb", null, 1);
                    ArrayList<User> a_u1 = handler1.getUser(sender_email);
                    String sender_name = a_u1.get(0).getName();
                    if(!(receiver_email.equalsIgnoreCase("Not Selected"))) {
                        ArrayList<User> a_u2 = handler1.getUser(receiver_email);
                        receiver_name = a_u2.get(0).getName();
                    }
                    sender_name_tv = view.findViewById(R.id.sender_name_tv);
                    sender_name_tv.setText(sender_name);
                    receiver_name_tv = view.findViewById(R.id.receiver_name_tv);
                    receiver_name_tv.setText(receiver_name);
                    amount_money_tv = view.findViewById(R.id.amount_money_tv);
                    amount_money_tv.setText("Rs. " + String.valueOf(amount));

                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(v.getContext());
                    alertDialogBuilder1.setTitle("Details");
                    alertDialogBuilder1.setView(view);
                    alertDialogBuilder1.setPositiveButton("Ok", null);
                    alertDialogBuilder1.setNegativeButton("Cancel", null);

                    final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                    alertDialog1.show();

                    alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View view = LayoutInflater.from(v.getContext()).inflate(R.layout.activity_process_transaction, null, false);
                            AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(v.getContext());
                            alertDialogBuilder2.setView(view);

                            final AlertDialog alertDialog2 = alertDialogBuilder2.create();
                            alertDialog2.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog2.dismiss();
                                    alertDialog1.dismiss();
                                    AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                                            .setMessage("Transaction Done Successfully!!")
                                            .setPositiveButton("Ok", null)
                                            .show();
                                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DBHandler handler1 = new DBHandler(v.getContext(), "bankdb", null, 1);
                                            handler1.Update(sender_email, receiver_email, amount);
                                            v.getContext().startActivity(new Intent(v.getContext(), TransactionMain_Activity.class));
                                        }
                                    });
                                }
                            }, 4000);
                        }
                    });
                }
            });
        }

    }

    // Step 1: Initialize the dataset of the Adapter.

    public CustomAdapter2(ArrayList<User> dataSet) {
        localDataSet = dataSet;
    }

    // Step 2: Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_row_item_2, viewGroup, false);

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

