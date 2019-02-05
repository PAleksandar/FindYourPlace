package com.example.aca.findyourplace;

import android.util.Log;

import com.google.gson.Gson;

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
        this.id=id;
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.isActive=isActive;
        this.birthday=birthday;
    }



    public User loadUser(int userID)
    {
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


}
