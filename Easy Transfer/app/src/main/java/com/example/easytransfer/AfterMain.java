package com.example.easytransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AfterMain extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DBHandler handler = new DBHandler(this, "bankdb", null, 1);
        arr = handler.getAllUsers();
        CustomAdapter c = new CustomAdapter(arr);
        recyclerView.setAdapter(c);
    }
}