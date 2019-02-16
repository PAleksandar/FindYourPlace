package com.example.aca.findyourplace;

import android.support.v4.app.FragmentManager;

public class StartChatFragment implements ICommand
{
    int userId;
    FragmentManager menager;

    public StartChatFragment(int userID, FragmentManager m)
    {
        this.userId=userID;
        this.menager=m;
    }

    @Override
    public void execute() {
        menager.beginTransaction().replace(R.id.home_fragment_container, new ChatFragment(userId)).commit();

    }
}
