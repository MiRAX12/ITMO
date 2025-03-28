package commands;

import managers.CollectionManager;
import io.XMLWriter;
import utility.Request;
import utility.Response;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;


public class Save extends Command {
    public Save (){super("save", "Сохранить текущую коллекцию в файл");
    }

    @Override
    public Response execute(Request request) {
        XMLWriter xmlWriter = new XMLWriter();
        try {
            xmlWriter.writeToFile();
        } catch (Exception e) {
            return new Response("Не удалось сохранить файл: " + e.getMessage());
        }
        return new Response("Файл сохранен\n");
    }

    @Override
    public String toString() {
        return getName();
    }
}
