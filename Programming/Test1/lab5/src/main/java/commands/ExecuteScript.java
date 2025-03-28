package commands;

import handlers.Handler;
import exceptions.ScriptRecursionException;
import handlers.Router;
import utility.Request;
import utility.Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;


public class ExecuteScript extends Command {
    private HashSet<String> executedFiles = new HashSet<>();

    public ExecuteScript() {
        super("execute_script", "Выполняет скрипт");
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response("Произошла непредвиденная ошибка");

        if (request.arg() == null || request.arg().isEmpty()) {
            return new Response("Укажите путь к файлу скрипта");
        }

        Path path = Paths.get(request.arg());
        if (!path.toFile().exists()) return new Response("Файл не существует");
        if (!path.toFile().isFile()) return new Response("Путь не ведет к файлу");
        if (!path.toFile().canRead()) return new Response("У вас нет прав на чтение файла");

        try {
            Scanner scanner = new Scanner(path);

            executedFiles.add(path.toFile().getCanonicalPath());
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().trim();
                Path scriptPath = Paths.get(Handler.parse(line).arg());

                if (executedFiles.contains(scriptPath.toFile().getCanonicalPath()))
                    throw new ScriptRecursionException();

                response = new Response(Router.getInstance()
                        .route(Handler.parse(line)).message());
            }
            executedFiles.clear();
        } catch (Exception e){
            response = new Response("Произошла ошибка %s".formatted(e.getMessage()));
        } return response;
    }

    @Override
    public String toString() {
        return getName();
    }
}



