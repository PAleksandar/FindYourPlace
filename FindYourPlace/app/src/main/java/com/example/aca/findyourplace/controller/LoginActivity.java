package com.example.aca.findyourplace.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.model.Comment;
import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.Message2;
import com.example.aca.findyourplace.model.Notification;
import com.example.aca.findyourplace.model.Place;
import com.example.aca.findyourplace.model.User;

import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn,btnRegister;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn=(Button) findViewById(R.id.btnSignIn);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignIn();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        type = getIntent().getStringExtra("type");
    }

    public void openSignIn()
    {

            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);


    }
    public void openRegister()
    {
        Intent intent=new Intent(this,ConversationActivity.class);
        //intent.putExtra("type",type);
        startActivity(intent);
    }

}
