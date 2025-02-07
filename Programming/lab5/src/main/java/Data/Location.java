package Data;

import Interfaces.Validatable;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null

    @Override
    public boolean validate() {
        if (x == null || y == null || z == null) return false;
        return true;
    }

    public static class builder{
        private Float x;
        private Float y;
        private Long z;
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
