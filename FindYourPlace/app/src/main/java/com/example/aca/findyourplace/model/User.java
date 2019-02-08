package com.example.aca.findyourplace.model;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;

import org.json.JSONException;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.aca.findyourplace.RabbitMQ.mreza;

public class User
{
    int id;
    String email;
    String password;
    String firstName;
    String lastName;
    boolean isActive;
    Date birthday;

    public User()
    {

    }

    public User(int id, String email, String password, String firstName, String lastName, boolean isActive, Date birthday)
    {
        this.id= id;
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.isActive=isActive;
        this.birthday=birthday;
    }

    public static User loadUserByEmail(String email, String password) throws JSONException {
        String us=null;
        DeleteDataTask gdt;
        gdt=new DeleteDataTask();
        try {
            us=gdt.execute(mreza+"user/email/"+email+"/"+password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User user=new User();
        Gson gson = new Gson();
        return user= gson.fromJson(us,User.class);
    }

    public static User loadUser(int userID) throws JSONException {
        String us=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            us=gdt.execute(mreza+"user/"+userID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User user=new User();
        Gson gson = new Gson();
        return user= gson.fromJson(us,User.class);
    }

    public void saveUser()
    {
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"user");
    }

    public void putUser()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"user/"+this.id);
    }

    public void deleteUser()
    {
        DeleteDataTask ddt = new DeleteDataTask();
        ddt.execute(RabbitMQ.mreza+"user/"+this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
