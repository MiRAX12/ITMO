package commands;

import constructors.ParameterConstructor;
import exceptions.ScriptRecursionException;
import utility.FileConfiguration;
import utility.Request;
import utility.Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Command to execute a script from a file.
 * <p>
 * The {@code ExecuteScript} command reads a script from a chosen file and returns
 * its content as a se in the {@link Response}. The command performs several checks
 * to ensure the file is valid and readable.
 * </p>
 *
 * @see Response
 *
 * @since 1.0
 */
public class ExecuteScript extends Command {
    private final HashSet<String> executedFiles = new HashSet<>();

    public ExecuteScript() {
        super("execute_script", "Выполняет скрипт");
    }

    /**
     * Executes the script from path given in argument
     * <p>
     * Reads lines from file containing commands and executing them.
     * </p>
     *
     * @param request the request containing the path to compare
     * @return a {@link Response} containing result of executing commands from the file
     */

    public Response execute(Request request) {
        return null;
    }

    /**
     * Overridden {code toString} to return name of this command
     *
     * @return name of the command
     */
    @Override
    public String toString() {
        return getName();
    }
}



