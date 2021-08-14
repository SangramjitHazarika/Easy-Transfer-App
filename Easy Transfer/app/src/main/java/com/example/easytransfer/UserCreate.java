package com.example.easytransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserCreate extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private TextView txtAccNo;
    private TextView txtBalance;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAccNo = findViewById(R.id.txtAccNo);
        txtBalance = findViewById(R.id.txtBalance);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f = 0;
                if(txtName.getText().toString().isEmpty()) {
                    txtName.setError("Field can't be empty.");
                    f = 1;
                }
                if(txtEmail.getText().toString().isEmpty()) {
                    txtEmail.setError("Field can't be empty.");
                    f = 1;
                }
                if(txtAccNo.getText().toString().isEmpty()) {
                    txtAccNo.setError("Field can't be empty.");
                    f = 1;
                }
                if(txtBalance.getText().toString().isEmpty()) {
                    txtBalance.setError("Field can't be empty.");
                    f = 1;
                }
                if(!(Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches())) {
                    txtEmail.setError("Invalid Email Address");
                    f = 1;
                }
                if(txtAccNo.getText().toString().length()!=11) {
                    txtAccNo.setError("Account Number must contain 11 digits");
                    f = 1;
                }

                if(f==0) {
                    User u = new User(txtName.getText().toString(), txtEmail.getText().toString(),txtAccNo.getText().toString(), Integer.parseInt(txtBalance.getText().toString()));
                    DBHandler handler = new DBHandler(getApplicationContext(), "bankdb", null, 1);
                    handler.addUser(u);
                    Toast.makeText(UserCreate.this, "User Created Successfully!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserCreate.this, MainActivity.class));
                }

            }
        });


    }
}