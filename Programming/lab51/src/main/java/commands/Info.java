package commands;

import managers.CollectionManager;
import utility.Request;
import utility.Response;

public class Info extends Command {
    public Info (){
        super("info", "Вывести информацию о коллекции" +
                "(кол-во элементов, тип коллекции, доступные ключи)");
    }

    @Override
    public Response execute(Request request) {
        CollectionManager collection = CollectionManager.getInstance();
        return new Response("Информация о коллекции:\n" +
                "Количество элементов : " + collection.getCollection().size() + "\n" +
                "Тип : " + collection.getCollection().getClass().getSimpleName() + "\n" +
                "Набор доступных ключей : " + collection.getCollection().keySet() + "\n");
    }

    @Override
    public String toString() {
        return getName();
    }
}
