package com.example.aca.findyourplace.model;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.aca.findyourplace.RabbitMQ.mreza;

public class Event {
    int id;
    String name;
    String tag;
    ArrayList<Byte> image;
    String description;
    int like;
    Date date;
    int placeId;
    int ownerUserId;

    public Event()
    {
        image =  new ArrayList<Byte>();
    }

    public Event(int id, String name, String tag, ArrayList<Byte> image, String description, int like
    , Date date, int placeId, int ownerUserId)
    {
        this.image = new ArrayList<Byte>();
        this.id=id;
        this.name=name;
        this.tag=tag;
        this.image=image;
        this.description=description;
        this.like=like;
        this.date=date;
        this.placeId = placeId;
        this.ownerUserId = ownerUserId;
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

    public ArrayList<Byte> getImage() {
        return image;
    }

    public void setImage(ArrayList<Byte> image) {
        this.image = image;
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
