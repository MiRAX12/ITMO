package commands;

import constructors.WorkerBuilder;
import handlers.Handler;
import exceptions.ScriptRecursionException;
import handlers.Router;
import model.Worker;
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
    @Override
    public Response execute(Request request) {
        Response response = new Response("Произошла непредвиденная ошибка");

        if (request.getArg() == null || request.getArg().isEmpty()) {
            return new Response("Укажите путь к файлу скрипта");
        }

        Path path = Paths.get(request.getArg());

        try {
            FileConfiguration.checkReadFile(path);
            Scanner scanner = new Scanner(path);

            executedFiles.add(path.toFile().getCanonicalPath());
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().trim();
                Path scriptPath = Paths.get(Handler.parse(line).getArg());

                if (executedFiles.contains(scriptPath.toFile().getCanonicalPath()))
                    throw new ScriptRecursionException();

//                if (line.equals("insert \\d+")){
//                    createWorker(Handler.parse(line));
//                    Worker worker = WorkerBuilder.build();
//                    request = new Request(command, arg, worker);
//                }
//
//                response = new Response(Router.getInstance()
//                        .route(Handler.parse(line)).getMessage());
            }
            executedFiles.clear();
        } catch (Exception e){
            response = new Response("Произошла ошибка %s".formatted(e.getMessage()));
        } return response;
    }

//    private Worker createWorker(Request request) {
//
//    }

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



