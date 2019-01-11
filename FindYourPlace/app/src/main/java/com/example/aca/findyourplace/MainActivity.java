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

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;

public class MainActivity extends AppCompatActivity {

    RabbitMQ RabbitCon = new RabbitMQ();
    Thread subscribeThread;
    Thread publishThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RabbitCon.setupConnectionFactory();
        RabbitCon.publishToAMQP(publishThread);
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

       // subscribe(incomingMessageHandler);

        String us=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            us=gdt.execute("http://192.168.1.113:5000/user/1").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(us==null)
        Log.d("test User get", "test uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        else
            Log.d("test User get", us);



        try {
            JSONObject jsonObject = new JSONObject(us);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User data = new User();
        Gson gson = new Gson();
        data= gson.fromJson(us,User.class);



        if(data.email!=null)
            Log.d("test User Json", data.email);



        RabbitCon.subscribe(incomingMessageHandler,subscribeThread);


    }

    void setupPubButton() {
        Button button = (Button) findViewById(R.id.publish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText et = (EditText) findViewById(R.id.text);
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
