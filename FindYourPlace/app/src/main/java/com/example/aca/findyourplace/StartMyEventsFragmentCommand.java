package com.example.aca.findyourplace;

import android.support.v4.app.FragmentManager;

import com.rabbitmq.client.Command;

public class StartMyEventsFragmentCommand implements ICommand {

    int userId;
    FragmentManager menager;

    public StartMyEventsFragmentCommand(int userID, FragmentManager m)
    {
        this.userId=userID;
        this.menager=m;
    }

    @Override
    public void execute() {
        //menager.beginTransaction().replace(R.id.home_fragment_container, new ChatFragment(userId)).commit();
        menager.beginTransaction().replace(R.id.home_fragment_container, new MyEventsFragment(userId)).commit();

    }
}
