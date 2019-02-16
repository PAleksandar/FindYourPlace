package com.example.aca.findyourplace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aca.findyourplace.controller.AddCommentActivity;
import com.example.aca.findyourplace.controller.LoginActivity;
import com.example.aca.findyourplace.model.Comment;
import com.example.aca.findyourplace.model.Event;
import com.example.aca.findyourplace.model.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventAdapter extends  RecyclerView.Adapter<EventAdapter.ViewHolder>{


    public Context mContext;
    public List<Event> mPost;

    public EventAdapter(Context mContext, List<Event> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    public void update(List<Event> eventList)
    {
        mPost=new ArrayList<>();
        mPost.addAll(eventList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false );
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Event event=mPost.get(position);

        if(event.getDescription().equals(""))
        {
            holder.description.setVisibility(View.GONE);
        }
        else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(event.getDescription());
        }

        holder.description.setText(event.getDescription());
        holder.likes.setText(Integer.toString(event.getLike()));
        try {
            User user=User.loadUser(event.getOwnerUserId());
            holder.username.setText(user.getFirstName()+" "+user.getLastName());

            try {
                byte [] encodeByte=Base64.decode(user.getImage(),Base64.DEFAULT);
                Bitmap image=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                holder.image_profile.setImageBitmap(image);
                holder.post_image.setImageBitmap(image);
            } catch(Exception e) {
                e.getMessage();

            }

            //holder.image_profile
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.comment.setOnClickListener( (v)->{

            // Intent intent = new Intent(mContext, AddCommentActivity.class);
            //mContext.startActivity(intent);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Write comment");


            final EditText input = new EditText(mContext);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
            builder.setView(input);


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text=input.getText().toString();
                    holder.comments.setText(text);

                    Comment com=new Comment(text,new Date(System.currentTimeMillis()),0,event.getOwnerUserId(),event.getPlaceId());
                    com.saveComment();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView image_profile, post_image, like, comment, save;
        public TextView username, likes, publisher, description, comments;
        public CircleImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            image_profile=itemView.findViewById(R.id.image_profile);
            post_image=itemView.findViewById(R.id.post_image);
            comments=itemView.findViewById(R.id.comments);
            like=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comment);

            save=itemView.findViewById(R.id.save);
            username=itemView.findViewById(R.id.username);
            likes=itemView.findViewById(R.id.likes);
            description=itemView.findViewById(R.id.description);

            //img=itemView.findViewById(R.id.profile_image_view);

        }
    }

    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publiser, final String id)
    {
        username.setText("test username");
        //publiser.setText("test publisher");
    }
}
