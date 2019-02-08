package com.example.aca.findyourplace.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.RabbitMQ;
import com.example.aca.findyourplace.model.PostDataTask;
import com.example.aca.findyourplace.model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText mEmail, mPassword, mName, mLastName;
    Button mImage, mFinish, mDate;
    Date date;
    private int mYear, mMonth, mDay;
    private final static int Gallery_Pick = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (MaterialEditText) findViewById(R.id.edtEmail);
        mPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        mName = (MaterialEditText) findViewById(R.id.edtName);
        mLastName = (MaterialEditText) findViewById(R.id.lastName);

        mImage = (Button) findViewById(R.id.button3);
        mFinish = (Button) findViewById(R.id.button);
        mDate=(Button) findViewById(R.id.dateButton);
        date=new Date(System.currentTimeMillis());


        mFinish.setOnClickListener((view)->{

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String name = mName.getText().toString();
            String lastName = mLastName.getText().toString();
            String us=null;

            //int id, String email, String password, String firstName, String lastName, boolean isActive, Date birthday
            User user=new User(0,email,password,name,lastName,true,date);
            PostDataTask pdt = new PostDataTask();
            // pdt.SetJSONMessage(et.getText().toString(),1,2,1);
            pdt.SetJsonObject(user);
            try {
                us=pdt.execute(RabbitMQ.mreza+"user").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            JsonObject jsonObject = new JsonParser().parse(us).getAsJsonObject();
            int userId=jsonObject.get("id").getAsInt();

            Intent intent = new Intent(this, StartPageActivity.class);
            intent.putExtra("User",userId);
            startActivity(intent);

        });


        mDate.setOnClickListener((view)->{

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                          //  Log.d("Datum", "godina: "+year+", mesec: "+monthOfYear+", dan: "+dayOfMonth);
                            date=new Date(year-1900,monthOfYear,dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        });

    }





}