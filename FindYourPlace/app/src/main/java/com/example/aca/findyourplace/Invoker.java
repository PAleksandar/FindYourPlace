package com.example.aca.findyourplace;

import java.util.HashMap;
import java.util.Map;

public class Invoker {

    public Invoker(){}

    private Map<Integer, ICommand> commandMap = new HashMap<>();

    public void addCommand(Integer s,ICommand command)
    {
        this.commandMap.put(s,command);
    }

    public ICommand getCommand(Integer s)
    {
        return this.commandMap.get(s);
    }

}
