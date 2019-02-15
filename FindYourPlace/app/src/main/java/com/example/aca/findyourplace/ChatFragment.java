package com.example.aca.findyourplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aca.findyourplace.controller.ChatActivity;
import com.example.aca.findyourplace.controller.StartPageActivity;
import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment {


    private RecyclerView recyclerView;
    private ConversationAdapter conversationAdapter;
    private List<Conversation> conversationList;
    private int userId;

    @SuppressLint("ValidFragment")
    public ChatFragment(int userId)
    {
        this.userId=userId;
    }

    public ChatFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_chat,container,false);


        recyclerView=view.findViewById(R.id.chat_fragment_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        conversationList=new ArrayList<>();
        try {
            readConversations();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        conversationAdapter=new ConversationAdapter (getContext(),conversationList);
        recyclerView.setAdapter(conversationAdapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        /*
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Position "+position+" test: "+conversationList.get(position).getUser2()+" user id "+userId);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();

                        alert11.show();

                         */
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("Conversation",conversationList.get(position).getId());
                       // intent.putExtra("ConversationUser",conversationList.get(position).getUser2());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return view;
    }

    private void readConversations() throws JSONException {

        // int id, String name, String tag,/* ArrayList<Byte> image,*/ String description, int like
        // , Date date, int placeId, int ownerUserId
        conversationList=Conversation.loadConversationForUser(userId);
        //conversationList.add(new Conversation(1,2,1));
        //conversationList.add(new Conversation(2,3,4));
        //conversationList.add(new Conversation(3,3,5));
    }

}
