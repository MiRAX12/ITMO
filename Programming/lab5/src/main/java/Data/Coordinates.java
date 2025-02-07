package Data;

import Interfaces.Validatable;

public class Coordinates implements Validatable {
    private float x; //Максимальное значение поля: 603
    private Long y; //Поле не может быть null

    public static class CoordinatesBuilder {
        private float x = 0.0f;
        private Long y = 0L;

        public CoordinatesBuilder() {
            super();
        }

        public CoordinatesBuilder paramX(float x) {
            this.x = x;
            return this;
        }

        public CoordinatesBuilder paramY(Long y){
            this.y = y;
            return this;
        }

        public Coordinates build(){
            Coordinates coordinates = new Coordinates();
            coordinates.x = this.x;
            coordinates.y = this.y;

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
