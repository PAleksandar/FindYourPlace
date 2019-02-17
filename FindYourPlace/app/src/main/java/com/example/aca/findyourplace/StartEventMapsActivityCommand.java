package com.example.aca.findyourplace;

import android.content.Context;
import android.content.Intent;

import com.example.aca.findyourplace.controller.AddEventActivity;
import com.example.aca.findyourplace.controller.EventMapsActivity;

public class StartEventMapsActivityCommand implements ICommand {

    Context context;

    public StartEventMapsActivityCommand(Context c)
    {

        this.context=c;
    }

    @Override
    public void execute() {

        Intent intent = new Intent(context, EventMapsActivity.class);
        // intent.putExtra("EventId",eventId);
        context.startActivity(intent);

    }
}
