package utility;


import Network.*;
import constructors.WorkerBuilder;
import model.*;
import exceptions.ExitWritten;
import сlient.Client;

import java.io.*;
import java.util.*;

public class Handler implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 5505;
    private final Scanner scanner = new Scanner(System.in);
    private static final Client client = new Client(HOST, PORT);
    Authorization authorization = new Authorization(scanner, new User(null, null));
    private static ExecuteScript executeScript = new ExecuteScript();


    public Handler() {
    }

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

        } catch (Exception e) {
            System.out.println("Введите любой символ, чтобы повторить попытку или exit, чтобы закрыть клиент");
        }
        while (user == null) {
            try {
                authorization.authorize(scanner);
                LogIn.authenticateUser(scanner);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNext()) {
            try {
                parseConsoleInput(scanner);

            } catch (Exception e) {
                if (e.getMessage() != null) System.out.println("Ошибка " + e.getMessage());
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
//                    if (arg.isEmpty()) {
//                        client.sendToServer(new Request(user, command));
//                        client.receiveFromServer();
//                        break;
//                    }
                    Worker worker = WorkerBuilder.build();
                    request = new RequestBuilder().setUser(user).setCommand(command).setWorker(worker).build();

                    client.sendToServer(request);
                    client.receiveFromServer();
                    break;
                case "execute_script":
                    executeScript.execute(new RequestBuilder().setUser(user).setCommand(command).setArg(arg).build());
                    break;
                default:
                    request = new RequestBuilder().setUser(user).setCommand(command).setArg(arg).build();
                    client.sendToServer(request);
                    client.receiveFromServer();
                    break;
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printResponse(Response response) {
        System.out.println(response.getMessage());
        if (response.getWorkers() != null) {
            System.out.println(response.getWorkers());
        }
    }

    public Scanner getScanner() {
        return scanner;
    }


}

