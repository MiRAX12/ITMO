//import Commands.Add;
import commands.*;
import constructors.ParameterConstructor;
import constructors.WorkerBuilder;
import data.Worker;
import managers.CollectionManager;
import managers.CommandManager;
import utility.Engine;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.LinkedHashMap;

//import static Managers.DumpManager.xmlDeserialize;
//import static Managers.DumpManager.xmlSerialize;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        LinkedHashMap<String, Worker> workers = new LinkedHashMap<>();
        CollectionManager collectionManager = new CollectionManager(workers);
        CommandManager commandManager = new CommandManager();
        Engine engine = new Engine(commandManager);
        Show show = new Show(collectionManager);

        commandManager.setUpCommand(new Help(collectionManager));
        commandManager.setUpCommand(new Exit());
        commandManager.setUpCommand(new Show(collectionManager));
        commandManager.setUpCommand(new Clear(collectionManager));
        commandManager.setUpCommand(new Info(collectionManager));
        commandManager.setUpCommand(new Save(collectionManager));
//        System.out.println(commandManager.getCommands());
//        engine.runProgramm();
        collectionManager.add(WorkerBuilder.build());
        show.execute();
//        DumpManager dumpManager = new DumpManager();
//
//
//
//
////        CommandManager.setUpCommand(new Add(CollectionManager));
//
//        System.out.println(commandManager.getCommands().toString());
//        engine.runProgramm();
//        xmlSerialize();
//        System.out.println(xmlDeserialize());


//
//        catch (JsonProcessingException e) {
//            System.out.println("exception JSON");
//        } catch (IOException e) {
//            System.out.println("exception IO");
    }
}



