package com.example.aca.findyourplace;

import android.content.Context;
import android.content.Intent;

import com.example.aca.findyourplace.controller.AddEventActivity;

public class StartAddEventActivityCommand implements ICommand {

    Context context;
    int userId;

    public StartAddEventActivityCommand(Context c, int userId)
    {

        this.context=c;
        this.userId=userId;
    }

    @Override
    public void execute() {

        Intent intent = new Intent(context, AddEventActivity.class);
        intent.putExtra("UserId",userId);
        context.startActivity(intent);

    }
}
