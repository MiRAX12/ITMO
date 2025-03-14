package commands;

import chat.Runner;
import managers.CommandManager;
import utility.Request;
import utility.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class ExecuteScript extends Command {

    public ExecuteScript() {
        super("execute_script", "Выполняет скрипт");
    }

    @Override
    public Response execute(Request request) {
        if (request.arg() == null || request.arg().isEmpty()) {
            return new Response("Укажите путь к файлу скрипта");
        }
        try {
            File file = Path.of(request.arg()).toFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String scriptLine = scanner.nextLine().trim();
                System.out.println(scriptLine);
                System.out.println(CommandManager.getInstance().
                        executeCommand(Runner.parse(scriptLine)).message());
                System.out.println(("Выполняю %s".formatted(Runner.parse(scriptLine).command())));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
        return new Response("Исполнение завершено");
    }

    @Override
    public String toString() {
        return getName();
    }
}



