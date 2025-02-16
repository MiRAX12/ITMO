//import Commands.Add;
import commands.*;
import data.Worker;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Engine;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.LinkedHashMap;

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

        commandManager.setUpCommand(new Help(collectionManager));
        commandManager.setUpCommand(new Exit());
        commandManager.setUpCommand(new Show(collectionManager));
        commandManager.setUpCommand(new Clear(collectionManager));
        commandManager.setUpCommand(new Info(collectionManager));
        commandManager.setUpCommand(new Save(collectionManager));

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



