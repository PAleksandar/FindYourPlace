package com.example.aca.findyourplace.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.RabbitMQ;
import com.example.aca.findyourplace.model.PostDataTask;
import com.example.aca.findyourplace.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText mEmail, mPassword, mName, mPhone;
    Button mImage, mFinish;
    private final static int Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        mPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        mName = (MaterialEditText) findViewById(R.id.edtName);
        mPhone = (MaterialEditText) findViewById(R.id.edtPhone);

        mImage = (Button) findViewById(R.id.button3);
        mFinish = (Button) findViewById(R.id.button);


        mFinish.setOnClickListener((view)->{

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String name = mName.getText().toString();
             //String phone = mPhone.getText().toString();

            //int id, String email, String password, String firstName, String lastName, boolean isActive, Date birthday
            User user=new User(1,email,password,name,name,true,new Date(System.currentTimeMillis()));
            PostDataTask pdt = new PostDataTask();
            // pdt.SetJSONMessage(et.getText().toString(),1,2,1);
            pdt.SetJsonObject(user);
            pdt.execute(RabbitMQ.mreza+"user");

            Intent intent = new Intent(this, StartPageActivity.class);
            startActivity(intent);

        });

    }



}