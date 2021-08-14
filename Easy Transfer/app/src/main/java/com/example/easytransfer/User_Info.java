package com.example.easytransfer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class User_Info extends AppCompatActivity {

    TextView name;
    TextView email_id;
    TextView account_no;
    TextView balance;
    Button transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        name = findViewById(R.id.name_tv);
        email_id = findViewById(R.id.email_id_tv);
        account_no = findViewById(R.id.account_no_tv);
        balance = findViewById(R.id.cur_balance_tv);
        transfer = findViewById(R.id.transfer);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sp.getString("sender_email","");
        DBHandler handler = new DBHandler(this, "bankdb", null, 1);
        ArrayList<User> a1 = handler.getUser(email);
        name.setText(a1.get(0).getName());
        email_id.setText(a1.get(0).getEmail_id());
        account_no.setText(a1.get(0).getAccount_no());
        balance.setText(String.valueOf(a1.get(0).getBalance()));

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

    }

    public void openDialog() {

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_transfer_money_1, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enter amount");
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();
        }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean isError = false;
                final EditText txtNumber = view.findViewById(R.id.moneyInput);
                String no = txtNumber.getText().toString().trim();
                if(no.isEmpty()) {
                    isError = true;
                    txtNumber.setError("Field can't be empty!!");
                }
                if(!isError) {
                    if(Integer.parseInt(String.valueOf(balance.getText()))<Integer.parseInt(no)) {
                        txtNumber.setError("Insufficient Balance");
                    }
                    else {
                        int money = Integer.parseInt(txtNumber.getText().toString());
                        alertDialog.dismiss();
                        AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                                .setMessage("Are you sure you want to continue with the transaction?")
                                .setPositiveButton("Yes", null)
                                .setNegativeButton("No", null)
                                .show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                View view = inflater.inflate(R.layout.progress_layout, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                                alertDialogBuilder.setView(view);

                                final AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.dismiss();
                                        dialog.dismiss();
                                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                                        SharedPreferences.Editor ed = sp.edit();
                                        ed.putInt("amount", money);
                                        ed.apply();
                                        String email = sp.getString("sender_email","");
                                        DBHandler handler = new DBHandler(getApplicationContext(), "bankdb", null, 1);
                                        int count = handler.getCount(email);
                                        if (count==0) {
                                            Toast.makeText(v.getContext(), "Sorry, No receiver as of now", Toast.LENGTH_LONG).show();
                                        } else {
                                            v.getContext().startActivity(new Intent(v.getContext(), Beneficiary_Details.class));
                                        }

                                    }
                                }, 1200);
                            }
                        });
                    }
                }
            }
        });
    }
}