package org.exampleMirax;//import Commands.Add;

import handlers.Handler;
import commands.CommandList;

public class Main {

    public static void main(String[] args) {
        Handler handler = new Handler();
        CommandList.setRunner(handler);
        handler.run();

    }
}



