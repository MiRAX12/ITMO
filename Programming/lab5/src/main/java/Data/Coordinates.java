package Data;

import Interfaces.Validatable;

import java.util.Scanner;

public class Coordinates implements Validatable {
    private float x; //Максимальное значение поля: 603
    private Long y; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public static class CoordinatesBuilder {
        private float x = 0.0f;
        private Long y = 0L;

        public CoordinatesBuilder() {
            super();
        }

        public void setX() {
            while (true) {
                System.out.print("Координата Х: ");
                try {
                    this.x = Float.parseFloat(consoleRead.nextLine().trim());
                    break;
                }catch(NumberFormatException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public void setY() {
            while (true) {
                System.out.print("Координата Y: ");
                try {
                    y = Long.valueOf(consoleRead.nextLine().trim());
                    break;
                }catch(NumberFormatException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public Coordinates build(){
            Coordinates coordinates = new Coordinates();
            setX();
            setY();
            return coordinates;
        }
    }

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
}
