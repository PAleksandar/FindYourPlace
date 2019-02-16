package com.example.aca.findyourplace;

import android.content.Context;
import android.content.Intent;

import com.example.aca.findyourplace.controller.LoginActivity;
import com.example.aca.findyourplace.controller.StartPageActivity;
import com.example.aca.findyourplace.model.User;

import java.util.concurrent.ExecutionException;

public class StartLoginActivity implements ICommand {

    int userId;
    Context context;

    public StartLoginActivity(int userId, Context c)
    {
        this.userId=userId;
        this.context=c;
    }

    @Override
    public void execute() {
        try {
            User.isActive(userId,false);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
