package com.example.aca.findyourplace.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aca.findyourplace.Chat1to1Fragment;
import com.example.aca.findyourplace.HomeFragment;
import com.example.aca.findyourplace.R;
import com.example.aca.findyourplace.model.Conversation;

public class ChatActivity extends AppCompatActivity {

    private int conversatioId;
    private int userId;
    private Conversation conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        conversation= (Conversation) getIntent().getSerializableExtra("Conversation");
        conversatioId=conversation.getId();
        userId = (int) getIntent().getExtras().get("UserId");


        getSupportFragmentManager().beginTransaction().replace(R.id.chat_fragment_container, new Chat1to1Fragment(conversation,userId)).commit();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(Integer.toString(conversatioId));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();

       // alert11.show();
    }
}
