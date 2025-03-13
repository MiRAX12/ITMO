package data;

import java.util.Scanner;

public class Location {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null

    public void setX(Float x) {
        if (x==null) throw new IllegalArgumentException("x не может быть null");
        this.x = x;
    }

    public void setY(Float y) {
        if (y==null) throw new IllegalArgumentException("y не может быть null");
        this.y = y;
    }

    public void setZ(Long z) {
        if (z==null) throw new IllegalArgumentException("z не может быть null");
        this.z = z;
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
