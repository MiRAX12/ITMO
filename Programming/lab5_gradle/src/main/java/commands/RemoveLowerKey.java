package commands;

import data.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.util.Map;

public class RemoveLowerKey extends Command {

    public RemoveLowerKey() {
        super("remove_lower_key",
                "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }

    public Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            collection.entrySet().removeIf(entry -> entry.getKey()
                    < Integer.parseInt(request.arg()));
            int difference = collectionSize - collection.size();
            response = new Response("Удалено %d элементов".formatted(difference));
        } catch (Exception e) {
            response = new Response("Чтобы удалить Worker, укажите через пробел ключ в виде числа");
        }
        return response;
    }

    @Override
    public String toString() {
        return getName();
    }
}

