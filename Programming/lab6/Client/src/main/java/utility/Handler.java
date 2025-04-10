package utility;

import constructors.CoordinatesBuilder;
import constructors.LocationBuilder;
import constructors.PersonBuilder;
import constructors.WorkerBuilder;
import model.*;
import exceptions.ExitWritten;

import java.io.IOException;
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
    private static final Client client = new Client("", 5505);

    /**
     * Executes the main logic of the handler.
     * <p>
     * Welcomes the user, loads the collection, resets IdGenerator
     * and processes input lines from the console sequentially.
     * </p>
     */
    @Override
    public void run() {
        //TODO убрать загрузку на сервер
//        isRunning = true;
        client.init();
        while (consoleRead.hasNext()) {
            try {

                var line = consoleRead.nextLine().trim();
                final String[] parts = line.split(" ", 2);
                final String command = parts[0];
                final String arg = parts.length > 1 ? parts[1] : "";
                processInput(command, arg);
                client.receiveFromServer();
            } catch (Exception e) {
                System.out.println("ыыы: " + e.getMessage());
            }
        }
    }

    public static void processInput(String command, String arg) throws IOException {
        Request request;
        switch (command) {
            case "exit":
                System.out.println(new ExitWritten().getMessage()); //TODO пробросить ошибку
                System.exit(0);
                break;
            case "insert":
                Worker worker = WorkerBuilder.build();
                request = new Request(command, arg, worker);
                client.sendToServer(request);
                break;
            case "execute_script":
                ExecuteScript.execute(arg);
                break;
            default:
                request = new Request(command, arg);
                client.sendToServer(request);
                break;
        }
    }

//    /**
//     * Set isRunning as false to interrupt program execution
//     */
//    public static void finish(){
//        isRunning = false;
//    }

//    /**
//     * Parses an input line into a {@link Request}.
//     * <p>
//     * Splits the input into command and argument
//     * </p>
//     *
//     * @param line the raw input line
//     * @return a {@link Request} object containing parsed data
//     * @see Request
//     */
//    public static Request parse(final String line) {
//        final String[] parts = line.split(" ", 2);
//        final String command = parts[0];
//        final String arg = parts.length > 1 ? parts[1] : "";
//        return new Request(command, arg);
//    }
}

