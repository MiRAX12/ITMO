package org.exampleMirax;//import Commands.Add;

import chat.Runner;
import commands.CommandList;

public class Main {

    public static void main(String[] args) {
        Runner runner = new Runner();
        CommandList.setRunner(runner);

        runner.run();


    }
}



