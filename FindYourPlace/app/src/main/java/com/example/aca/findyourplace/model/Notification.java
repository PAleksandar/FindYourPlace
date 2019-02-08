package com.example.aca.findyourplace.model;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.aca.findyourplace.RabbitMQ.mreza;

public class Notification {
    int id;
    String text;
    Date date;
    int userId;
    int eventId;

    public Notification(){}

    public Notification(int id, String text, Date date, int userId, int eventId)
    {
        this.id=id;
        this.text=text;
        this.date=date;
        this.userId=userId;
        this.eventId=eventId;
    }

    public static Notification loadNotification(int notificationID) throws JSONException
    {
        String nf=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            nf=gdt.execute(mreza+"notification/"+notificationID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Notification notification=new Notification();
        Gson gson = new Gson();
        return notification= gson.fromJson(nf,Notification.class);
    }

    public void saveNotification()
    {
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"notification");
    }

    public void putNotification()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"notification/"+this.id);
    }

    public void deleteNotification()
    {
        DeleteDataTask ddt = new DeleteDataTask();
        ddt.execute(RabbitMQ.mreza+"notification/"+this.id);
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
