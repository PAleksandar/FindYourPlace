package com.example.aca.findyourplace;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aca.findyourplace.model.Event;

import java.util.List;

public class EventAdapter extends  RecyclerView.Adapter<EventAdapter.ViewHolder>{


    public Context mContext;
    public List<Event> mPost;

    public EventAdapter(Context mContext, List<Event> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
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

        publisherInfo(holder.image_profile,holder.username,holder.publisher,"1");
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView image_profile, post_image, like, comment, save;
        public TextView username, likes, publisher, description, comments;

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
        }
    }

    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publiser, final String id)
    {
        username.setText("test username");
        //publiser.setText("test publisher");
    }
}
