package com.example.aca.findyourplace;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aca.findyourplace.controller.ChatActivity;
import com.example.aca.findyourplace.controller.StartPageActivity;
import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.Message2;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Chat1to1Fragment extends Fragment {


    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message2> messageList;
    private Conversation conversation;
    private int userId;
    private int receiverId;
    private ImageButton sendBtn;
    private EditText inputText;

    RabbitMQ RabbitCon = new RabbitMQ();
    Thread subscribeThread;
    Thread publishThread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RabbitCon.setupConnectionFactory();

        if(userId==conversation.getUser1())
        {
            receiverId=conversation.getUser2();
        }
        else
        {
            receiverId=conversation.getUser1();
        }

        final Handler incomingMessageHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                String message = msg.getData().getString("msg");

                Message2 newMessage=new Message2(message,receiverId,userId,conversation.getId(),new Date(System.currentTimeMillis()));
                messageList.add(newMessage);
                messageAdapter.update(messageList);

            }
        };

        String subId= String.valueOf(conversation.getId())+String.valueOf(receiverId);
        RabbitCon.subscribe(incomingMessageHandler,subscribeThread,String.valueOf(subId));


    }

    @SuppressLint("ValidFragment")
    public Chat1to1Fragment(Conversation conversation, int userId)
    {
        this.conversation=conversation;
        this.userId=userId;

    }

    public Chat1to1Fragment()
    {

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.fragment_chat1to1,container,false);

        sendBtn=(ImageButton) view.findViewById(R.id.send_message);
        inputText=(EditText) view.findViewById(R.id.input_message);

        recyclerView=view.findViewById(R.id.chat1to1_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageList=new ArrayList<>();
        try {
            readConversations();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        messageAdapter=new MessageAdapter (getContext(),messageList);
        recyclerView.setAdapter(messageAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Input: ", inputText.getText().toString());


               Message2 msg=new Message2(inputText.getText().toString(),userId,receiverId,conversation.getId(),null);

                try {
                    msg.saveMessage();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                inputText.setText("");

                msg.setDate(new Date(System.currentTimeMillis()));
               // messageList.add(msg);
                //messageAdapter.update(messageList);

                String publishId=String.valueOf(conversation.getId())+String.valueOf(userId);
                RabbitCon.publishToAMQP(publishThread,String.valueOf(publishId));
                RabbitCon.publishMessage(msg.getTekst());

            }
        });

        return view;
    }

    private void readConversations() throws JSONException {

         messageList=Message2.loadMessages(conversation.getId());

    }

}
