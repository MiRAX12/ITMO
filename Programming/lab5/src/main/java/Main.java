//import Commands.Add;
import Commands.*;
import Data.Coordinates;
import Data.Worker;
import Managers.CollectionManager;
import Managers.CommandManager;
import Managers.DumpManager;
import Utility.Engine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static Managers.DumpManager.xmlDeserialize;
import static Managers.DumpManager.xmlSerialize;

//import static Managers.DumpManager.xmlDeserialize;
//import static Managers.DumpManager.xmlSerialize;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        CollectionManager collectionManager = new CollectionManager();
        LinkedHashMap<String, Worker> workers = new LinkedHashMap<>();
        CommandManager commandManager = new CommandManager(collectionManager);
        Engine engine = new Engine(commandManager);

        collectionManager.setCollection(workers);
        collectionManager.add(Worker.build());
        collectionManager.add(Worker.build());

        System.out.println(collectionManager.getCollection());

        commandManager.setUpCommand(new Help());
        commandManager.setUpCommand(new Exit());
        commandManager.setUpCommand(new Show());
        commandManager.setUpCommand(new Clear());
        commandManager.setUpCommand(new Info());
        commandManager.setUpCommand(new Save());

        DumpManager dumpManager = new DumpManager();

        engine.runProgramm();


//        CommandManager.setUpCommand(new Add(CollectionManager));

        System.out.println(commandManager.getCommands().toString());
        engine.runProgramm();
//        xmlSerialize();
//        System.out.println(xmlDeserialize());


//
//        catch (JsonProcessingException e) {
//            System.out.println("exception JSON");
//        } catch (IOException e) {
//            System.out.println("exception IO");
    }
}



