package model;

public class Coordinates {
    private float x; //Максимальное значение поля: 603
    private Long y; //Поле не может быть null

    public void setX(float x) {
        this.x = x;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static class Builder {
        private float x = 0;
        private Long y = null;

        public Builder x(float x) {
            this.x = x;
            return this;
        }

        public Builder y(Long y) {
            this.y = y;
            return this;
        }


        public Coordinates build() {
            Coordinates coordinates = new Coordinates();
            coordinates.y = this.y;
            coordinates.x = this.x;

            return coordinates;
        }
    }
    public float getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}
