package commands;

import data.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.util.Map;

public class RemoveGreater extends Command {

    public RemoveGreater() {
        super("remove_greater",
                "Удаляет всех Worker с зарплатой выше заданной");
    }

    @Override
    public Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            collection.entrySet().removeIf(entry -> entry.getValue()
                    .getSalary() > Float.parseFloat(request.arg()));
            int difference = collectionSize - collection.size();
            response = new Response("Удалено %d элементов".formatted(difference));
        }catch (Exception e) {
            response = new Response("Чтобы удалить Worker, укажите через пробел зарплату в виде числа");
        }
        return response;
    }

    @Override
    public String toString() {
        return getName();
    }
}
