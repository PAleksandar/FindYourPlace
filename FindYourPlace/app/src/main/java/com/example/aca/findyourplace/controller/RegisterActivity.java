package com.example.aca.findyourplace.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.aca.findyourplace.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText mEmail,mPassword,mName,mPhone;
    Button mImage,mFinish;
    private final  static int Gallery_Pick=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail=(MaterialEditText) findViewById(R.id.edtEmail);
        mPassword=(MaterialEditText) findViewById(R.id.edtPassword);
        mName=(MaterialEditText) findViewById(R.id.edtName);
        mPhone=(MaterialEditText) findViewById(R.id.edtPhone);

        mImage = (Button) findViewById(R.id.button3);
        mFinish = (Button) findViewById(R.id.button);
    }
}
