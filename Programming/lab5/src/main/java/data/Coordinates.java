package data;

import java.util.Scanner;

public class Coordinates {
    private float x; //Максимальное значение поля: 603
    private Long y; //Поле не может быть null
    private static final Scanner consoleRead = new Scanner(System.in);

    public void setX(float x) {
        if (x>603) throw new IllegalArgumentException("x не должен превышать 603");
        this.x = x;
    }

    public void setY(Long y) {
        if (y==null) throw new IllegalArgumentException("y не может быть null");
        this.y = y;
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
