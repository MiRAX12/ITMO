package Data;

import Interfaces.Validatable;

import java.util.Scanner;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);


    public static class LocationBuilder {
        private Float x = 0F;
        private Float y = 0F;
        private Long z = 0L;

        public LocationBuilder(){
            super();
        }

        public void setX() {
            while (true) {
                System.out.print("Координата Х: ");
                try {
                    this.x = Float.valueOf(consoleRead.nextLine().trim());
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
                    y = Float.valueOf(consoleRead.nextLine().trim());
                    break;
                }catch(NumberFormatException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public void setZ() {
            while (true) {
                System.out.print("Координата Z: ");
                try {
                    z = Long.valueOf(consoleRead.nextLine().trim());
                    break;
                }catch(NumberFormatException e) {
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public Location build() {
            Location location = new Location();
            setX();
            setY();
            setZ();
            return location;
        }
    }

    @Override
    public boolean validate() {
        if (x == null || y == null || z == null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
