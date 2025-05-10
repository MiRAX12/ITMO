package utility;


import constructors.WorkerBuilder;
import model.*;
import exceptions.ExitWritten;
import сlient.Client;

import java.io.*;
import java.util.*;

public class Handler implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 5505;
    private final Scanner consoleRead = new Scanner(System.in);
    private static final Client client = new Client(HOST, PORT);
    private static ExecuteScript executeScript = new ExecuteScript();


    public Handler() {}

    /**
     * Executes the main logic of the handler.
     * <p>
     * Welcomes the user, loads the collection, resets IdGenerator
     * and processes input lines from the console sequentially.
     * </p>
     */
    @Override
    public void run() {
        System.out.println("Подключение...");
        try {
            client.init();
            System.out.println("Подключено к серверу " + HOST + ":" + PORT);
        } catch (Exception e){
            System.out.println("Введите любой символ, чтобы повторить попытку или exit, чтобы закрыть клиент");
        }
            while (consoleRead.hasNext()) {
                try {
                    parseConsoleInput(consoleRead);

                } catch (Exception e) {
                    if (e.getMessage()!=null) System.out.println("Ошибка " + e.getMessage());
                }
            }
        }

    public static void parseConsoleInput(Scanner scanner) throws IOException {
        var line = scanner.nextLine().trim();
        final String[] parts = line.split(" ", 2);
        final String command = parts[0];
        final String arg = parts.length > 1 ? parts[1] : "";
        processInput(command, arg);
    }

    private static void processInput(String command, String arg) throws IOException {
        Request request;
        try {

        switch (command) {
            case "exit":
                System.out.println(new ExitWritten().getMessage());
                System.exit(0);
                break;
            case "insert":
                if (arg.isEmpty()) {
                    client.sendToServer(new Request(command));
                    client.receiveFromServer();
                    break;
                }
                Worker worker = WorkerBuilder.build();
                request = new Request(command, arg, worker);

                client.sendToServer(request);
                client.receiveFromServer();
                break;
            case "execute_script":
                executeScript.execute(new Request(command, arg));
                break;
            default:
                request = new Request(command, arg);
                    client.sendToServer(request);
                    client.receiveFromServer();
                break;
        }
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}

