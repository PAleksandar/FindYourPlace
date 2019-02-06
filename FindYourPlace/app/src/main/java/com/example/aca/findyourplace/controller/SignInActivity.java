package com.example.aca.findyourplace.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.model.User;

public class SignInActivity extends AppCompatActivity {

    private EditText txtEmail,txtPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtPassword=(EditText) findViewById(R.id.edtPassword);
        btnLogin=(Button) findViewById(R.id.zamain);

        btnLogin.setOnClickListener((view)->{
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            if(password.equals("")||email.equals(""))
            {
                return;
            }

           // User
            Intent intent = new Intent(this, StartPageActivity.class);
            startActivity(intent);
        });
    }
}
