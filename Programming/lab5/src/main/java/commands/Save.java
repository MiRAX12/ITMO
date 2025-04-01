package commands;

import managers.CollectionManager;
import io.XMLWriter;
import utility.Request;
import utility.Response;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Command to write all workers from collection to file
 *
 * @since 1.0
 */
public class Save extends Command {
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
            return new Response("Не удалось сохранить файл: " + e.getMessage());
        }
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
