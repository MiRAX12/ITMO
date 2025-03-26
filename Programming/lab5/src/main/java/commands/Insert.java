package commands;

import constructors.WorkerBuilder;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.io.IOException;

public class Insert extends Command {

    public Insert() {
        super("insert", "Добавляет Worker с заданным ключом");
    }

    @Override
    public Response execute(Request request) {
        Response response;

        try {
            Integer key = Integer.parseInt(request.arg());
            CollectionManager.getInstance()
                             .addElement(WorkerBuilder.build(), key);
            response = new Response("Новый Worker с ключом %d добавлен".formatted(key));
        } catch (NullPointerException e) {
            response = new Response(e.getMessage());
        } catch (NumberFormatException e) {
            response = new Response("Чтобы добавить Worker, укажите через пробел" +
                    " числовой ключ: insert {ключ}\n");
        }
        return response;
    }

    @Override
    public String toString() {
        return getName();
    }
}
