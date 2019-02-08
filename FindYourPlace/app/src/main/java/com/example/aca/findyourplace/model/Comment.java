package com.example.aca.findyourplace.model;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;

import org.json.JSONException;

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

    public static Comment loadComment(int commentID) throws JSONException {
        String cm=null;
        DeleteDataTask gdt;
        gdt=new DeleteDataTask();
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
