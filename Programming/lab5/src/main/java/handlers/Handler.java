package handlers;

import managers.CollectionManager;
import utility.Request;

import java.util.*;

public class Handler implements Runnable {
    private static boolean isRunning;
    private final Scanner consoleRead = new Scanner(System.in);

    @Override
    public void run() {
        System.out.println("Добро пожаловать");
        CollectionManager.getInstance().load();
        isRunning = true;
        while (consoleRead.hasNext()&&isRunning) {
            try {
                    var line = consoleRead.nextLine().trim();
                    System.out.println(Router.getInstance().route(parse(line)).message());


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void finish(){
        isRunning = false;
    }

    public static Request parse(final String line) {
        final String[] parts = line.split(" ", 2);
        final String command = parts[0];
        final String arg = parts.length > 1 ? parts[1] : "";
        return new Request(command, arg);
    }
}

