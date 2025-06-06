package commands;

import network.User;
import managers.CollectionManager;
import model.Worker;
import network.Request;
import network.Response;

/**
 * Command to initialize creation of new {@link model.Worker} instance
 * and insert it to a collection.
 *
 * @see model.Worker
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
            Worker worker = request.getWorker();
            User user = request.getUser();
            CollectionManager.getInstance()
                    .addElement(worker, user);
            response = new Response("Новый Worker добавлен");
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
