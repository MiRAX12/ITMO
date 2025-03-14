package commands;

import managers.CollectionManager;
import utility.Request;
import utility.Response;

public class RemoveGreaterKey extends Command {

    public RemoveGreaterKey() {
        super("remove_lower_key",
                "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }

    public Response execute(Request request) {
        var iterator = CollectionManager.getInstance().getCollection().entrySet().iterator();

        int count = 0;

        while (iterator.hasNext()) {
            var entry = iterator.next();
            System.out.print("Сравниваем элементы:" + entry.getKey() + " vs " + request.arg());
            if (entry.getKey() > Integer.parseInt(request.arg())) {
                System.out.println("Элемент " + entry +" больше, удаление...");
                iterator.remove();
                count++;
            }
            else
            {
                return new Response("Элемент меньше");
            }
        }
        return new Response("Удалено " + count + " элементов");
    }

    @Override
    public String toString() {
        return getName();
    }
}