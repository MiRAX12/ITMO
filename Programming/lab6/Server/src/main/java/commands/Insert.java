package commands;

import constructors.WorkerBuilder;
import io.IdGenerator;
import managers.CollectionManager;
import model.Worker;
import utility.Request;
import utility.Response;

import java.io.IOException;

/**
 * Command to initialize creation of new {@link model.Worker} instance
 * and insert it to a collection.
 *
 * @see model.Worker
 *
 * @since 1.0
 */
public class Insert extends Command {

    public Insert() {
        super("insert", "Добавляет Worker с заданным ключом");
    }

    /**
     * Initializes building of a new {@link model.Worker} instance and adding it to a collection
     * by given key
     *
     * @param request the request containing key to be set as new's worker id
     * @return a {@link Response} indicates if elements was inserted.
     */
    @Override
    public Response execute(Request request) {
        Response response;
        try {
            Integer key = Integer.parseInt(request.getArg());
            Worker worker = request.getWorker();
            worker.setId(IdGenerator.getInstance().generateId());
            CollectionManager.getInstance()
                             .addElement(worker, key);
            response = new Response("Новый Worker с ключом %d добавлен".formatted(key));
        } catch (NullPointerException e) {
            response = new Response(e.getMessage());
        } catch (NumberFormatException e) {
            response = new Response("Чтобы добавить Worker, укажите через пробел" +
                    " числовой ключ: insert {ключ}\n");
        }
        return response;
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
