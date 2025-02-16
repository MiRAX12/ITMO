package data;

import interfaces.Validatable;

import java.util.Scanner;

import static data.ParameterConstructor.askParameterFloat;
import static data.ParameterConstructor.askParameterLong;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public static Location build(){
        Location location = new Location();
        location.x = askParameterFloat("Введите координату Х локации: ");
        location.y = askParameterFloat("Введите координату Y локации: ");
        location.z = askParameterLong("Введите координату Z локации: ");
        return location;
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

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public Long getZ() {
        return z;
    }
}
