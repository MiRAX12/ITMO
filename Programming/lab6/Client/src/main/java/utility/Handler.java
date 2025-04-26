package utility;

import constructors.CoordinatesBuilder;
import constructors.LocationBuilder;
import constructors.PersonBuilder;
import constructors.WorkerBuilder;
import exceptions.FileNotExistsException;
import model.*;
import exceptions.ExitWritten;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

///**
// * Handles user input.
// * <p>
// * This class is responsible for reading input from the user, parsing server.utility.commands to request,
// * routing requests to {@link Router}, and printing responses to the console.
// * </p>
// *
// * @author Mirax
// * @see Router
// * @since 1.0
// */
public class Handler implements Runnable {
    private final Scanner consoleRead = new Scanner(System.in);
    private static final Client client = new Client("localhost", 5505);
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
        client.init();
            while (consoleRead.hasNext()) {
                try {
                    parseConsoleInput(consoleRead);

                } catch (Exception e) {
                    System.out.println("Сервер отключен: " + e.getMessage());
                    /**
                     * 1. Сервер отключился
                     */
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

