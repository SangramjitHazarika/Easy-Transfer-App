package com.example.easytransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TransactionMain_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Transaction_Record> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_main);
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sender_email = sp.getString("sender_email","");
        String receiver_email = sp.getString("receiver_email","");
        DBHandler_2 handler2 = new DBHandler_2(this, "bank1db", null, 1);
        if(!(receiver_email.equalsIgnoreCase("receiver"))) {
            String result = sp.getString("result", "");
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String date_time = dateTimeFormatter.format(dateTime);
            int amount = sp.getInt("amount", 0);

            DBHandler handler1 = new DBHandler(getApplicationContext(), "bankdb", null, 1);
            ArrayList<User> a_u1 = handler1.getUser(sender_email);
            String sender_name = a_u1.get(0).getName();
            String receiver_name = "Not Selected";

            if (!(receiver_email.equalsIgnoreCase("Not Selected"))) {
                ArrayList<User> a_u2 = handler1.getUser(receiver_email);
                receiver_name = a_u2.get(0).getName();
            }

            handler2.addTransactionRecord(new Transaction_Record(sender_name, sender_email, receiver_name, receiver_email, amount, date_time, result));
        }
        arr = handler2.getAllTransactions();

        CustomAdapter3 c3 = new CustomAdapter3(arr);
        recyclerView.setAdapter(c3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }
}