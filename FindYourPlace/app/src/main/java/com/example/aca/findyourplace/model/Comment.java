package com.example.aca.findyourplace.model;

import android.util.Log;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.aca.findyourplace.RabbitMQ.mreza;

public class Comment {
    int id;
    String text;
    Date date;
    int like;
    int userId;
    int placeId;

    public Comment()
    {

    }

    public Comment(int id, String text, Date date, int like, int userId, int placeId)
    {
        this.id=id;
        this.text=text;
        this.date=date;
        this.like=like;
        this.userId=userId;
        this.placeId=placeId;
    }

    public Comment(String text, Date date, int like, int userId, int placeId)
    {
        //this.id=null;
        this.text=text;
        this.date=date;
        this.like=like;
        this.userId=userId;
        this.placeId=placeId;
    }

    public static Comment loadComment(int commentID) throws JSONException {
        String cm=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            cm=gdt.execute(mreza+"comment/"+commentID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Comment comment=new Comment();
        Gson gson = new Gson();
        return comment= gson.fromJson(cm,Comment.class);
    }

    public static ArrayList<Comment> loadComments(int placeId)
    {
        String text;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            text=gdt.execute(mreza+"comment/user/"+placeId).get();

            Log.d("cccc", text);
            Type listType = new TypeToken<ArrayList<Comment>>(){}.getType();
            ArrayList<Comment> data = new ArrayList<Comment>();
            Gson gson = new Gson();
            data= gson.fromJson(text,listType);
            Integer size = data.size();
            Log.d("list",size.toString());

            return data;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return new ArrayList<Comment>();

    }



    public void saveComment()
    {
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"comment");
    }

    public void putComment()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"comment/"+this.id);
    }

    public void deleteComment()
    {
        DeleteDataTask ddt = new DeleteDataTask();
        ddt.execute(RabbitMQ.mreza+"comment/"+this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
