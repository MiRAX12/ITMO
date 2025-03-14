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
        if (CollectionManager.getInstance().getCollection().isEmpty()){
            return new Response("Коллекция пуста!");
        }
        try {
            XMLWriter xmlWriter = new XMLWriter();
            xmlWriter.writeToFile();
            return new Response("Файл сохранен");
        } catch (XMLStreamException e) {
            System.out.println("Ошибка сериализации");
        } catch (IOException e){
            System.out.println("Ошибка ввода");
        }
        return Response.empty();
        }

    @Override
    public String toString() {
        return getName();
    }
}
