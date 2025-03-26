package commands;

import managers.CollectionManager;
import utility.Request;
import utility.Response;

public class Clear extends Command{
    public Clear(){
        super("clear", "Очищает коллекцию");
    }

    @Override
    public Response execute(Request request) {
        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        CollectionManager.getInstance().getCollection().clear();
            return new Response("Коллекция успешно очищена!");
    }


    @Override
    public String toString() {
        return getName();
    }
}

