//import Commands.Add;
import Data.Coordinates;
import Data.Worker;
import Managers.CollectionManager;
import Managers.CommandManager;
import Managers.DumpManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//import static Managers.DumpManager.xmlDeserialize;
//import static Managers.DumpManager.xmlSerialize;

public class Main {
    DumpManager dumpManager = new DumpManager();
    CollectionManager collectionManager = new CollectionManager(dumpManager);
    static LinkedHashMap<Long, Worker> workers = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException, XMLStreamException {
//        LinkedHashMap<>
        CollectionManager CollectionManager;
//        CommandManager.setUpCommand(new Add(CollectionManager));
        System.out.println(Coordinates.build());

//        xmlSerialize();
//        System.out.println(xmlDeserialize());


//
//        catch (JsonProcessingException e) {
//            System.out.println("exception JSON");
//        } catch (IOException e) {
//            System.out.println("exception IO");
    }
}



