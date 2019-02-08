package com.example.aca.findyourplace.model;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import static com.example.aca.findyourplace.RabbitMQ.mreza;

public class Conversation {
    int id;
    int user1;
    int user2;

    public Conversation(){}

    public Conversation(int id, int user1,int user2)
    {
        this.id=id;
        this.user1=user1;
        this.user2=user2;
    }

    public static Conversation loadConversation(int conversationID) throws JSONException
    {
        String cv=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            cv=gdt.execute(mreza+"conversation/"+conversationID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Conversation conversation=new Conversation();
        Gson gson = new Gson();
        return conversation= gson.fromJson(cv,Conversation.class);
    }

    public void saveConversation()
    {
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"conversation");
    }

    public void putConversation()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"conversation/"+this.id);
    }

    public void deleteConversation()
    {
        DeleteDataTask ddt = new DeleteDataTask();
        ddt.execute(RabbitMQ.mreza+"conversation/"+this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }
}
