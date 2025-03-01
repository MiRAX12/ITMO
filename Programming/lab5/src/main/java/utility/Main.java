package utility;//import Commands.Add;

import chat.Runner;
import commands.CommandList;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

//import static Managers.DumpManager.xmlDeserialize;
//import static Managers.DumpManager.xmlSerialize;

public class Main {

    public static void main(String[] args) {
        Runner runner = new Runner();
        CommandList.setRunner(runner);

        runner.run();

    }
}



