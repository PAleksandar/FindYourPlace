package com.example.aca.findyourplace;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {



    private CountDownTimer countDownTimer;
    private int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimer();
    }

    private void startTimer()
    {
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Log.d("Tick", "onTick: ");
                count++;
                if(count>3)
                {
                    stopTimer();
                }
            }
            public void onFinish() {
                // Log.d("finish", "onFinish: ");
            }
        };

        countDownTimer.start();
    }
    private void stopTimer()
    {
        countDownTimer.cancel();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }





    public void enterConversation(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}

