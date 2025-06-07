package commands;

import database.Database;
import managers.CollectionManager;
import model.Worker;
import network.Request;
import network.Response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class RemoveById extends Command {

    public RemoveById() {
        super("remove_by_id",
                "удалить из коллекции все элементы," +
                        " id которого эквивалентно заданному");
    }


    public synchronized Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Long, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            collection.entrySet().removeIf(entry -> {
                boolean remove = entry.getValue().getId() == Long.parseLong(request.getArg());
                if (remove) return Database.deleteById(entry.getKey(), request.getUser());
                return false;
            });
            int difference = collectionSize - collection.size();
            if (difference == 0) response = new Response("You don't have permission");
            else response = new Response("Elements removed %d".formatted(difference));
        } catch (DateTimeParseException e) {
            response = new Response("Чтобы удалить Worker, укажите через пробел" +
                    " дату и время конца в формате 'yyyy-MM-dd HH:mm:ss z'" +
                    " (например, '2023-10-05 14:30:00 UTC')");
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

