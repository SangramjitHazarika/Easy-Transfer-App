package com.example.easytransfer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.ArrayList;

public class Beneficiary_Details extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_details);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String sender_email = sp.getString("sender_email", "");

        DBHandler handler = new DBHandler(this, "bankdb", null, 1);
        arr = handler.getReceiverDetails(sender_email);
        CustomAdapter2 c2 = new CustomAdapter2(arr);
        recyclerView.setAdapter(c2);
    }

    @Override
    public void onBackPressed() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Do you want to cancel the transaction?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("result", "Failed");
                ed.putString("receiver_email", "Not Selected");
                ed.putInt("amount", 0);
                ed.apply();
                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                        .setMessage("Transaction Failed!!")
                        .setPositiveButton("Ok", null)
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.getContext().startActivity(new Intent(v.getContext(), TransactionMain_Activity.class));
                    }
                });
            }
        });



    }
}