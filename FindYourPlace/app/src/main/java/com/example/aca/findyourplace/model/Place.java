package com.example.aca.findyourplace.model;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static com.example.aca.findyourplace.RabbitMQ.mreza;

public class Place {
    int id;
    String name;
    String tag;
    double latitude;
    double longitude;
    //ArrayList<Byte> image;
    String description;
    int like;

    public Place()
    {
        //image = new ArrayList<Byte>();
    }

    public Place(int id,String name,String tag,
                 double latitude,double longitude/*, ArrayList<Byte> image*/, String description,int like)
    {
        //this.image = new ArrayList<Byte>();
        this.id=id;
        this.name=name;
        this.tag=tag;
        this.latitude=latitude;
        this.longitude = longitude;
        //this.image=image;
        this.description=description;
        this.like=like;
    }

    public static Place loadPlace(int placeID) throws JSONException
    {
        String pl=null;
        DeleteDataTask gdt;
        gdt=new DeleteDataTask();
        try {
            pl=gdt.execute(mreza+"place/"+placeID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Place place=new Place();
        Gson gson = new Gson();
        return place= gson.fromJson(pl,Place.class);
    }

    public void savePlace()
    {
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"place");
    }

    public void putPlace()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"place/"+this.id);
    }

    public void deletePlace()
    {
        DeleteDataTask ddt = new DeleteDataTask();
        ddt.execute(RabbitMQ.mreza+"place/"+this.id);
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

//    public ArrayList<Byte> getImage() {
//        return image;
//    }
//
//    public void setImage(ArrayList<Byte> image) {
//        this.image = image;
//    }

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
}
