package com.example.aca.findyourplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aca.findyourplace.controller.ChatActivity;
import com.example.aca.findyourplace.controller.StartPageActivity;
import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.Message2;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Chat1to1Fragment extends Fragment {


    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message2> messageList;
    private int conversatioId;
    private int conversationUserId;

    @SuppressLint("ValidFragment")
    public Chat1to1Fragment(int conversatioId)
    {
        this.conversatioId=conversatioId;

    }

    public Chat1to1Fragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_chat1to1,container,false);

        recyclerView=view.findViewById(R.id.chat1to1_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
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

        return view;
    }

    private void readConversations() throws JSONException {

         messageList=Message2.loadMessages(conversatioId);

    }

}
