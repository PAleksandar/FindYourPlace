package com.example.aca.findyourplace;

import java.util.Date;

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


}
