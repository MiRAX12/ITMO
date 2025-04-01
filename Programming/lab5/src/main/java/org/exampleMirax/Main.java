package org.exampleMirax;//import Commands.Add;

import handlers.Handler;
import commands.CommandList;
import model.Worker;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax
 * @see Handler
 */
public class Main {

    public static void main(String[] args) {
        Handler handler = new Handler();

        handler.run();

    }
}



