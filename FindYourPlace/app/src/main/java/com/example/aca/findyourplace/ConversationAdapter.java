package com.example.aca.findyourplace;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aca.findyourplace.controller.LoginActivity;
import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends  RecyclerView.Adapter<ConversationAdapter.ViewHolder>{


    public Context mContext;
    public List<Conversation> mConversations;
    public int userId;

    public ConversationAdapter(Context mContext, List<Conversation> mPost,int userId) {
        this.mContext = mContext;
        this.mConversations = mPost;
        this.userId=userId;
    }

    public void update(List<Conversation> newList)
    {
        mConversations=new ArrayList<>();
        mConversations.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.conversation_item, parent, false );
        return new ConversationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter.ViewHolder holder, int position) {

        Conversation conversation=mConversations.get(position);


            int id;
            if(userId==conversation.getUser1())
            {
                id=conversation.getUser2();
            }
            else {
                id=conversation.getUser1();
            }



            try {
                User us = User.loadUser(id);
                holder.username.setText(us.getFirstName()+"  "+us.getLastName());
                //////////////////////////////////
                try {
                    byte [] encodeByte=Base64.decode(us.getImage(),Base64.DEFAULT);
                    Bitmap image=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    holder.image_profile.setImageBitmap(image);
                } catch(Exception e) {
                    e.getMessage();

                }

                //img.setImageBitmap(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }


    }

    @Override
    public int getItemCount() {
        return mConversations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView image_profile;
        public TextView username;

        public ViewHolder(View itemView) {
            super(itemView);

            image_profile=itemView.findViewById(R.id.conversation_fragment_profile_image);
            username=itemView.findViewById(R.id.conversation_fragment_username);

            image_profile.setOnClickListener( (v)->{

                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            });

        }
    }

    private void publisherInfo(final ImageView image_profile, final TextView username)
    {
        username.setText("test username");



    }
}
