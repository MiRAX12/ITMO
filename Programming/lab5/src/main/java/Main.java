import Data.Coordinates;
import Data.Location;
import Data.Worker;
import Exceptions.AskBreak;
import Interfaces.Console;
import Console.StandardConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    static List<Worker> workers = new ArrayList<Worker>();

    public static void main(String[] args) {
        Console console = new StandardConsole();
        // ТЕСТ
//        Coordinates coordinates = new Coordinates.CoordinatesBuilder().paramX().paramY().build();
//        Location location = new Location.LocationBuilder().setX().paramY().paramZ().build();

        System.out.println(coordinates);
    }
}



