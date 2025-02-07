import Data.Coordinates;
import Data.Worker;

public class Main {
    public static void main(String[] args) {

        Coordinates coordinates = new Coordinates.CoordinatesBuilder(1).paramY(1L).build();
        System.out.println(coordinates.toString());
        System.out.println(coordinates.validate());

        Worker worker = new Worker.WorkerBuilder().build();
        System.out.println(worker.toString());
        System.out.println(worker.validate());
    }
}