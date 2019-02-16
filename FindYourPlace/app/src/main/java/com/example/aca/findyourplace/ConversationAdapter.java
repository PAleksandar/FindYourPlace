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
import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class ConversationAdapter extends  RecyclerView.Adapter<ConversationAdapter.ViewHolder>{


    public Context mContext;
    public List<Conversation> mConversations;

    public ConversationAdapter(Context mContext, List<Conversation> mPost) {
        this.mContext = mContext;
        this.mConversations = mPost;
    }

    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.conversation_item, parent, false );
        return new ConversationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter.ViewHolder holder, int position) {

        Conversation conversation=mConversations.get(position);



        try {
             User user=User.loadUser(conversation.getUser2());
             holder.username.setText(user.getFirstName()+"  "+user.getLastName());


            try {
                User us = User.loadUser(user.getId());
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

        }
    }

    private void publisherInfo(final ImageView image_profile, final TextView username)
    {
        username.setText("test username");



    }
}
