package client;

import client.exceptions.NoScriptPathException;
import common.Request;
import common.Response;
import exceptions.ScriptRecursionException;

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
public class ExecuteScript {

    /**
     * Executes the script from path given in argument
     * <p>
     * Reads lines from file containing server.utility.commands and executing them.
     * </p>
     * <p>
     * //     * @param request the request containing the path to compare
     */
    public static void execute(String pathToFile) throws NoScriptPathException {
        final HashSet<String> executedFiles = new HashSet<>();
        if (pathToFile == null || pathToFile.isEmpty()) {
            throw new NoScriptPathException();
        }
        Path path = Paths.get(pathToFile);
        try {
            FileConfiguration.checkReadFile(path);
            Scanner scanner = new Scanner(path);
            executedFiles.add(path.toFile().getCanonicalPath());
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().trim();
                final String[] parts = line.split(" ", 2);
                final String command = parts[0];
                final String arg = parts.length > 1 ? parts[1] : "";
                Path scriptPath = Paths.get(arg);
                if (executedFiles.contains(scriptPath.toFile().getCanonicalPath()))
                    throw new ScriptRecursionException();
                Handler.processInput(command, arg);
            }
            executedFiles.clear();
        } catch (Exception e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }
}



