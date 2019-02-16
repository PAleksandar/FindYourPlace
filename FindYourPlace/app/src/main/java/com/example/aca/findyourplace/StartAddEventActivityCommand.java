package com.example.aca.findyourplace;

import android.content.Context;
import android.content.Intent;

import com.example.aca.findyourplace.controller.AddEventActivity;

public class StartAddEventActivityCommand implements ICommand {

    Context context;

    public StartAddEventActivityCommand(Context c)
    {

        this.context=c;
    }

    @Override
    public void execute() {

        Intent intent = new Intent(context, AddEventActivity.class);
        context.startActivity(intent);

    }
}
