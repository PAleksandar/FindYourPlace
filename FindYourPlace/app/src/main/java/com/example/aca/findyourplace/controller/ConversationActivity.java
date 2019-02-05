package com.example.aca.findyourplace.controller;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aca.findyourplace.model.Message2;
import com.example.aca.findyourplace.model.PostDataTask;
import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.RabbitMQ;
import com.example.aca.findyourplace.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConversationActivity extends AppCompatActivity {

    User user=new User();

    RabbitMQ RabbitCon = new RabbitMQ();
    Thread subscribeThread;
    Thread publishThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        RabbitCon.setupConnectionFactory();
        // RabbitCon.publishToAMQP(publishThread,"chat");
        setupPubButton();
        TextView tv = (TextView) findViewById(R.id.textView);

        final Handler incomingMessageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");
                Date now;
                now = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
                //tv.append(ft.format(now) + ' ' + message + "\n");
                tv.append(message + "\n");

        }
        };


        // user=user.loadUser(1);

        if(user.getEmail()!=null)
            Log.d("test User Json 000000000000000000000000000000000000000", user.getEmail());

        ArrayList<Message2> listaPoruka = new Message2().loadMessages(1);
        for (int i = 0; i<listaPoruka.size();i++)
        {
            //SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
            //tv.append(ft.format(listaPoruka.get(i).getDate()) + " "+ listaPoruka.get(i).getTekst() +"\n");
            tv.append(listaPoruka.get(i).getTekst() +"\n");
        }
        RabbitCon.subscribe(incomingMessageHandler,subscribeThread,String.valueOf(user.getId()));


    }

    void setupPubButton() {



        Button button = (Button) findViewById(R.id.publish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText et = (EditText) findViewById(R.id.text);
                RabbitCon.publishToAMQP(publishThread,String.valueOf(user.getId()));
                RabbitCon.publishMessage(et.getText().toString());
                Message2 msg=new Message2(et.getText().toString(),1,2,1,new Date(System.currentTimeMillis()));

                PostDataTask pdt = new PostDataTask();
                pdt.SetJSONMessage(et.getText().toString(),1,2,1);
               // pdt.SetJsonObject(msg);
                pdt.execute(RabbitMQ.mreza+"message");
                et.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //publishThread.interrupt();
        //subscribeThread.interrupt();
    }
}
