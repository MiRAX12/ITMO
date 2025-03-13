package utility;//import Commands.Add;

import chat.Runner;
import commands.CommandList;
import constructors.CoordinatesBuilder;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Runner runner = new Runner();
        CommandList.setRunner(runner);

        runner.run();

    }
}



