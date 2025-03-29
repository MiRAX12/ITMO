package commands;

import managers.CollectionManager;
import utility.Request;
import utility.Response;

/**
 * Command to print an information about collection in memory
 *
 * @since 1.0
 */
public class Info extends Command {
    public Info (){
        super("info", "Вывести информацию о коллекции" +
                "(кол-во элементов, тип коллекции, доступные ключи)");
    }

    /**
     * Retrieves information about all available commands.
     * <p>
     * Return a response containing number of elements, type of collection and
     * available keys
     * </p>
     *
     * @param request unused for this command
     * @return a {@link Response} containing number of elements, type and keys
     */
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
