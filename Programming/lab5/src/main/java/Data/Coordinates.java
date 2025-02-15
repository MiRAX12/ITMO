package Data;

import Interfaces.Constructable;
import Interfaces.Validatable;
import Data.ParameterConstructor;
import java.util.Scanner;

import static Data.ParameterConstructor.askParameterFloat;
import static Data.ParameterConstructor.askParameterLong;

public class Coordinates implements Validatable {
    private float x; //Максимальное значение поля: 603
    private Long y; //Поле не может быть null
    private static final Scanner consoleRead = new Scanner(System.in);

    public static Coordinates build(){
        Coordinates coordinates = new Coordinates();
        coordinates.x = askParameterFloat("Введите координату Х: ");
        coordinates.y = askParameterLong("Введите координату Y: ");
        return coordinates;
    }

//    public static class CoordinatesBuilder {
//        private float x = 0.0f;
//        private Long y = 0L;
//
//
//
//        public CoordinatesBuilder setX() {
//            while (true) {
//                System.out.print("Координата Х: ");
//                try {
//                    this.x = Float.parseFloat(consoleRead.nextLine().trim());
//                    break;
//                }catch(NumberFormatException e) {
//                    System.out.println("Ошибка ввода ");
//                }
//            }
//            return this;
//        }
//
//        public CoordinatesBuilder setY() {
//            while (true) {
//                System.out.print("Координата Y: ");
//                try {
//                    y = Long.valueOf(consoleRead.nextLine().trim());
//                    break;
//                }catch(NumberFormatException e) {
//                    System.out.println("Ошибка ввода ");
//                }
//            }
//            return this;
//        }
//
//        public Coordinates build(){
//            Coordinates coordinates = new Coordinates();
//            coordinates.x = this.x;
//            coordinates.y = this.y;
//            return coordinates;
//        }
//    }

    @Override
    public boolean validate() {
        if (x>603) return false;
        if (y==null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}
