package com.example.aca.findyourplace.model;

import android.support.annotation.VisibleForTesting;

import com.example.aca.findyourplace.RabbitMQ;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
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
    ByteArrayOutputStream profileImage;
    String image;
    static String token="";

    public static String getUserToken()
    {
        return User.token;
    }

    public static void setUserToken (String token)
    {
        User.token = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ByteArrayOutputStream getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ByteArrayOutputStream profileImage) {
        this.profileImage = profileImage;
    }

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
        this.profileImage=null;
    }

    public User(int id, String email, String password, String firstName, String lastName, boolean isActive, Date birthday, String image)
    {
        this.id= id;
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.isActive=isActive;
        this.birthday=birthday;
        this.profileImage=null;
        this.image=image;
    }

    public static User loadUserByEmail(String email, String password) throws JSONException {
        String us=null;
        GetDataTask gdt;
        gdt=new GetDataTask();
        try {
            us=gdt.execute(mreza+"user/email/"+email+"/"+password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
       //JsonObject jsonObject= new JsonParser().parse(us).getAsJsonObject();
        //token=jsonObject.get("Headers").toString();//get("x-auth-token").toString();

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

    public String saveUser() throws ExecutionException, InterruptedException {
        String s=null;
        PostDataTask pdt = new PostDataTask();
        pdt.SetJsonObject(this);
        s=pdt.execute(RabbitMQ.mreza+"user").get();
        return s;
    }

    public void putUser()
    {
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(this);
        pdt.execute(RabbitMQ.mreza+"user/"+this.id);
    }

    public void isActive(boolean state) throws ExecutionException, InterruptedException {

        JsonObject jsonObject;
        if(state) {
             jsonObject = new JsonParser().parse("{\"isActive\": \"true\"}").getAsJsonObject();
        }
        else
        {
            jsonObject = new JsonParser().parse("{\"isActive\": \"false\"}").getAsJsonObject();
        }
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(jsonObject);
        pdt.execute(RabbitMQ.mreza+"user/isActive/"+this.id).get();
    }

    public static void isActive(int userId, boolean state) throws ExecutionException, InterruptedException {

        JsonObject jsonObject;
        if(state) {
            jsonObject = new JsonParser().parse("{\"isActive\": \"true\"}").getAsJsonObject();
        }
        else
        {
            jsonObject = new JsonParser().parse("{\"isActive\": \"false\"}").getAsJsonObject();
        }
        PutDataTask pdt = new PutDataTask();
        pdt.SetJsonObject(jsonObject);
        pdt.execute(RabbitMQ.mreza+"user/isActive/"+userId).get();
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
