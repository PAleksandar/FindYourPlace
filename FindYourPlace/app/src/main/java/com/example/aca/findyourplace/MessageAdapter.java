package com.example.aca.findyourplace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aca.findyourplace.model.Conversation;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.Message2;
import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.ViewHolder>{


    public Context mContext;
    public List<Message2> mMessages;

    public MessageAdapter(Context mContext, List<Message2> mPost) {
        this.mContext = mContext;
        this.mMessages = mPost;
    }

    public void update(List<Message2> newList)
    {
        mMessages=new ArrayList<>();
        mMessages.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false );
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {

        Message2 message=mMessages.get(position);


        holder.message_text.setText(message.getTekst());

        try {
            User us = User.loadUser(message.getSender());
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
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView image_profile;
        public TextView message_text;

        public ViewHolder(View itemView) {
            super(itemView);

            image_profile=itemView.findViewById(R.id.message_profile_image);
            message_text=itemView.findViewById(R.id.message_text);

        }
    }


}
