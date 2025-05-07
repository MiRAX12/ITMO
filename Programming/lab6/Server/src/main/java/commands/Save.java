package commands;

import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Server;
import utility.Request;
import utility.Response;

/**
 * Command to write all workers from collection to file
 *
 * @since 1.0
 */
public class Save extends Command {
    Logger logger = LoggerFactory.getLogger(Server .class);

    public Save (){super("save", "Сохранить текущую коллекцию в файл");
    }

    /**
     * Uses {@link CollectionManager} method {@code save} to write collection to file
     *
     * @param request unused for this command
     * @return a {@link Response} if collection was saved or not.
     */
    @Override
    public Response execute(Request request) {
        try {
            CollectionManager.getInstance().save();
        } catch (Exception e) {
            logger.error("Не удалось сохранить файл: " + e.getMessage());
            System.out.println("Не удалось сохранить файл: " + e.getMessage());
            return new Response("Не удалось сохранить файл: " + e.getMessage());
        }
        logger.info("Файл сохранен");
        System.out.println("Файл сохранен");
        return new Response("Файл сохранен\n");
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
