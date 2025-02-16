package utility;

import data.Worker;
import managers.CollectionManager;
import managers.CommandManager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Engine {
    private static boolean flag = true;
    CommandManager commandManager;
    public Engine(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

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
