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
        workers.add(Ask.askConsole(console));

        for (Console e: console) System.out.println(e);
    }
}

class Ask{
    public static Console askConsole(Console console) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("Enter Name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            var coordinates = askCoordinates(console);
            var location = askLocation(console);
            return new Worker(id, name, coordinates, location);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            int x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try { x = Integer.parseInt(line); break; }catch(NumberFormatException e) { }
                }
            }
            float y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try { y = Float.parseFloat(line); break; }catch(NumberFormatException e) { }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Location askLocation(Console console) throws AskBreak {
        try {
            Location x;
            while (true) {
                console.print("WeaponType ("+WeaponType.names()+"): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        r = WeaponType.valueOf(line); break;
                    } catch (NullPointerException | IllegalArgumentException  e) { }
                } else return null;
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}

