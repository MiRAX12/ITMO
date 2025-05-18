package utility;

import Network.Request;
import Network.Response;
import constructors.ParameterConstructor;
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
    private final HashSet<String> executedFiles = new HashSet<>();

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
        Response response = new Response("Произошла непредвиденная ошибка");

        if (request.getArg() == null || request.getArg().isEmpty()) {
            return new Response("Укажите путь к файлу скрипта");
        }

        Path path = Paths.get(request.getArg());

        try {
            FileConfiguration.checkReadFile(path);
            Scanner scanner = new Scanner(path.toFile());
            Scanner oldConsoleRead = ParameterConstructor.consoleRead;
            ParameterConstructor.consoleRead = scanner;
            Path scriptPath = Paths.get(request.getArg());

            if (executedFiles.contains(scriptPath.toFile().getCanonicalPath()))
                throw new ScriptRecursionException();
            executedFiles.add(path.toFile().getCanonicalPath());

            while (scanner.hasNext()) {
                Handler.parseConsoleInput(scanner);
            }

            ParameterConstructor.consoleRead = oldConsoleRead;
            scanner.close();
            executedFiles.clear();

        } catch (Exception e) {
            response = new Response("Произошла ошибка %s".formatted(e.getMessage()));
        }
        return response;
    }
}



