import Data.Coordinates;
import Data.Worker;
import Interfaces.Console;
import Console.StandardConsole;
import java
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Worker> workers = new ArrayList<Worker>();

    public static void main(String[] args) {
        Console console = new StandardConsole();
        Worker.add(Ask.askWorker(console, generate()));
    }
}

