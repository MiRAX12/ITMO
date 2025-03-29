package model;

import jakarta.validation.constraints.NotNull;

public class Location {

    @NotNull(message = "Координата x не может быть null")
    private Float x; //Поле не может быть null
    @NotNull(message = "Координата у не может быть null")
    private Float y; //Поле не может быть null
    @NotNull(message = "Координата z не может быть null")
    private Long z; //Поле не может быть null

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
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public static class Builder {
        private Float x = null;
        private Float y = null;
        private Long z = null;

        public Builder x(Float x) {
            this.x = x;
            return this;
        }

        public Builder y(Float y) {
            this.y = y;
            return this;
        }

        public Builder z(Long z) {
            this.z = z;
            return this;
        }

        public Location build() {
            Location location = new Location();
            location.y = this.y;
            location.x = this.x;
            location.z = this.z;
            return location;
        }

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
