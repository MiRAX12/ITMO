package data;

import java.util.Scanner;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setZ(Long z) {
        this.z = z;
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
