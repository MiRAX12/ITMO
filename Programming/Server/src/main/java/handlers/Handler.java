package handlers;

import managers.CollectionManager;
import utility.Request;

import java.util.*;

/**
 * Handles user input.
 * <p>
 * This class is responsible for reading input from the user, parsing commands to request,
 * routing requests to {@link Router}, and printing responses to the console.
 * </p>
 *
 * @author Mirax
 * @see Router
 * @since 1.0
 */
public class Handler implements Runnable {
    private static boolean isRunning;
    private final Scanner consoleRead = new Scanner(System.in);

    /**
     * Executes the main logic of the handler.
     * <p>
     * Welcomes the user, loads the collection, resets IdGenerator
     * and processes input lines from the console sequentially.
     * </p>
     */
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
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Set isRunning as false to interrupt program execution
     */
    public static void finish(){
        isRunning = false;
    }

    /**
     * Parses an input line into a {@link Request}.
     * <p>
     * Splits the input into command and argument
     * </p>
     *
     * @param line the raw input line
     * @return a {@link Request} object containing parsed data
     * @see Request
     */
    public static Request parse(final String line) {
        final String[] parts = line.split(" ", 2);
        final String command = parts[0];
        final String arg = parts.length > 1 ? parts[1] : "";
        return new Request(command, arg);
    }
}

