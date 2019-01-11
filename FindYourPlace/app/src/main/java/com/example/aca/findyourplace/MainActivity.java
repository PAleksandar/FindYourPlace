package com.example.aca.findyourplace;

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

    User user=new User();

    RabbitMQ RabbitCon = new RabbitMQ();
    Thread subscribeThread;
    Thread publishThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new PostDataTask().execute("http://localhost:5000/user/6");
        RabbitCon.setupConnectionFactory();
       // RabbitCon.publishToAMQP(publishThread,"chat");
        setupPubButton();

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");
                TextView tv = (TextView) findViewById(R.id.textView);
                Date now;
                now = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
                tv.append(ft.format(now) + ' ' + message + '\n');
            }
        };


       // user=user.loadUser(1);

        if(user.email!=null)
            Log.d("test User Json 000000000000000000000000000000000000000", user.email);


        new Message2().loadMessages(1);
        RabbitCon.subscribe(incomingMessageHandler,subscribeThread,String.valueOf(user.id));


    }

    void setupPubButton() {
        Button button = (Button) findViewById(R.id.publish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText et = (EditText) findViewById(R.id.text);
                RabbitCon.publishToAMQP(publishThread,String.valueOf(user.id));
                RabbitCon.publishMessage(et.getText().toString());
                et.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishThread.interrupt();
        subscribeThread.interrupt();
    }
}
