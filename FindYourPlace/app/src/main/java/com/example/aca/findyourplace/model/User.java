package com.example.aca.findyourplace.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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



    public User loadUser(int userID) throws JSONException {
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
       // if(us==null)
         //   Log.d("test User get", "test uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        //else
          //  Log.d("test User get", us);
        ///
        User user=new User();
        Gson gson = new Gson();
        return user= gson.fromJson(us,User.class);
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
