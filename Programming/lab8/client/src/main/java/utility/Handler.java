package utility;


import network.*;
import authorization.Authorization;
import constructors.WorkerBuilder;
import model.*;
import exceptions.ExitWritten;
import сlient.Client;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Handler implements Runnable {
    private static final String HOST = "localhost";
    private static final int PORT = 5505;
    private final Scanner scanner = new Scanner(System.in);
    private static User user;
    private static final Client client = Client.getInstance();
    private Authorization authorization = new Authorization(scanner, user, client);
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
        var flag = false;
        System.out.println("Подключение...");
        try {
            client.init();
            System.out.println("Подключено к серверу " + HOST + ":" + PORT);
            flag = true;
        } catch (Exception e) {
            System.out.println("Введите любой символ, чтобы повторить попытку или exit, чтобы закрыть клиент");
        }
        if (flag) do {
            try {
                user = authorization.authorize();
                System.out.println("Приветствую вас, " + user.getUsername() + " вы можете вводить команды\n" +
                        "Введите help чтобы получить список команд");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

        } while (scanner.hasNext() && user == null);
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

    public static void processInput(String command, String arg) throws IOException {
        Request request;
        Response response;
        User user = Client.getInstance().getUser();
        try {
            switch (command) {
                case "exit":
                    System.out.println(new ExitWritten().getMessage());
                    System.exit(0);
                    break;
                case "insert":
                    Worker worker = WorkerBuilder.build();
                    request = new RequestBuilder().setUser(user).setCommand(command).setWorker(worker).build();
                    client.sendToServer(request);
                    response = client.receiveFromServer();
                    if (response != null) System.out.println(response.getMessage());
                    break;
                case "execute_script":
                    executeScript.execute(new RequestBuilder().setUser(user).setCommand(command).setArg(arg).build());
                    break;
                default:
                    request = new RequestBuilder().setUser(user).setCommand(command).setArg(arg).build();
                    client.sendToServer(request);
                    response = client.receiveFromServer();
                    if (response != null) System.out.println(response.getMessage());
                    break;
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void executeInsert(Worker worker){
        User user = Client.getInstance().getUser();
        Request request = new RequestBuilder().setUser(user).setCommand("insert").setWorker(worker).build();
        client.sendToServer(request);
    }

    public static Map<Long, Worker> getMap() {
        User user = Client.getInstance().getUser();
        Request request = new RequestBuilder().setUser(user).setCommand("show").build();
        client.sendToServer(request);
        try {
            return client.receiveFromServer().getWorkers();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

