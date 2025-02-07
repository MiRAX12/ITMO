package Data;

import Interfaces.Validatable;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private Float y; //Поле не может быть null
    private Long z; //Поле не может быть null

    public static class LocationBuilder {
        private Float x = 0F;
        private Float y = 0F;
        private Long z = 0L;

        public LocationBuilder(Float X){
            super();
        }

        public LocationBuilder parmaX(float X){
            this.x = X;
            return this;
        }

        public LocationBuilder paramY(float y) {
            this.y = y;
            return this;
        }

        public LocationBuilder paramZ(long z) {
            this.z = z;
            return this;
        }

        public Location build() {
            Location location = new Location();
            location.x = x;
            location.y = y;
            location.z = z;
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
