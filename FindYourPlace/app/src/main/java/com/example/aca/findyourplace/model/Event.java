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

public class Event {
    int id;
    String name;
    String tag;

    String description;
    int like;
    Date date;
    int placeId;
    int ownerUserId;
    String image;

    public Event()
    {
        //image =  new ArrayList<Byte>();
    }

    public Event(int id, String name, String tag, String description, int like, Date date, int placeId, int ownerUserId, String image)
    {
        //this.image = new ArrayList<Byte>();
        this.id=id;
        this.name=name;
        this.tag=tag;
        //this.image=image;
        this.description=description;
        this.like=like;
        this.date=date;
        this.placeId = placeId;
        this.ownerUserId = ownerUserId;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Event loadEvent(int eventID) throws JSONException {
        String ev=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            ev=gdt.execute(mreza+"event/"+eventID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Event event=new Event();
        Gson gson = new Gson();
        return event= gson.fromJson(ev,Event.class);
    }

    public static ArrayList<Event> loadEvents()
    {
        String text;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            text=gdt.execute(mreza+ "event").get();

           // Log.d("cccc", text);
            Type listType = new TypeToken<ArrayList<Event>>(){}.getType();
            ArrayList<Event> data = new ArrayList<Event>();
            Gson gson = new Gson();
            data= gson.fromJson(text,listType);
            Integer size = data.size();
           // Log.d("list",size.toString());

            return data;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return new ArrayList<Event>();

    }

    public static ArrayList<Event> loadEvents(int userId)
    {
        String text;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            text=gdt.execute(mreza+ "event/user/"+userId).get();

            // Log.d("cccc", text);
            Type listType = new TypeToken<ArrayList<Event>>(){}.getType();
            ArrayList<Event> data = new ArrayList<Event>();
            Gson gson = new Gson();
            data= gson.fromJson(text,listType);
            Integer size = data.size();
            // Log.d("list",size.toString());

            return data;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return new ArrayList<Event>();

    }

    public void saveEvent()
    {
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"event/"+placeId);
    }

    public void putEvent()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"event/"+this.id);
    }

    public void deleteEvent()
    {
        DeleteDataTask ddt = new DeleteDataTask();
        ddt.execute(RabbitMQ.mreza+"event/"+this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }
}
