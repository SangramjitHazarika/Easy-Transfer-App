package com.example.easytransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button createUser;
    private Button transferMoney;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sp.edit();
        String receiver_email = sp.getString("receiver_email","");
        if(!(receiver_email.equalsIgnoreCase(""))) {
            ed.putString("receiver_email", "receiver");
            ed.apply();
            startActivity(new Intent(this, TransactionMain_Activity.class));
        } else {
            startActivity(new Intent(this, NoTransaction.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createUser = findViewById(R.id.createacc);
        transferMoney = findViewById(R.id.transf_money);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserCreate.class));
            }
        });

        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler handler = new DBHandler(getApplicationContext(), "bankdb", null, 1);
                int count = handler.getCount();
                if (count==0) {
                    Toast.makeText(MainActivity.this, "Sorry, No sender as of now, Create one!!", Toast.LENGTH_LONG).show();
                }else {
                    startActivity(new Intent(MainActivity.this, AfterMain.class));
                }
            }
        });



    }
}