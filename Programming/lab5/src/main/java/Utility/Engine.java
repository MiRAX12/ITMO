package Utility;

import Managers.CommandManager;

import java.io.IOException;
import java.util.Scanner;

public class Engine {
    private static boolean flag = true;
    CommandManager commandManager;

    public Engine(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Engine(){}

    public static void finishProgramm (){
        flag=false;
    }

    public void runProgramm() throws IOException {
        do {
            Scanner consoleRead = new Scanner(System.in);

            commandManager.setUserRequest(consoleRead.nextLine().trim().split(" "));
        } while (flag);
    }
}
