package model;

import jakarta.validation.constraints.NotNull;

/**
 * Location class.
 */
public class Location {

    @NotNull(message = "Координата x не может быть null")
    private Float x; //Поле не может быть null
    @NotNull(message = "Координата у не может быть null")
    private Float y; //Поле не может быть null
    @NotNull(message = "Координата z не может быть null")
    private Long z; //Поле не может быть null

    /**
     * set x coordinate.
     *
     * @param x coordinate
     */
    public void setX(Float x) {
        this.x = x;
    }

    /**
     * set y coordinate.
     *
     * @param y coordinate
     */
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * set z coordinate.
     *
     * @param z coordinate
     */
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

    /**
     * A Builder's pattern
     */
    public static class Builder {
        private Float x = null;
        private Float y = null;
        private Long z = null;

        /**
         * @param x - x coordinate
         *
         * @return - builder's object of written value
         */
        public Builder x(Float x) {
            this.x = x;
            return this;
        }

        /**
         * @param y - y coordinate
         *
         * @return - builder's object of written value
         */
        public Builder y(Float y) {
            this.y = y;
            return this;
        }

        /**
         * @param z - z coordinate
         *
         * @return - builder's object of written value
         */
        public Builder z(Long z) {
            this.z = z;
            return this;
        }

        /**
         * Build a Location instance
         *
         * @return {@link Location} instance
         */
        public Location build() {
            Location location = new Location();
            location.y = this.y;
            location.x = this.x;
            location.z = this.z;
            return location;
        }

    }

    /**
     * get X coordinate.
     *
     * @return x coordinate
     */
    public Float getX() {
        return x;
    }

    /**
     * get Y coordinate.
     *
     * @return y coordinate
     */
    public Float getY() {
        return y;
    }

    /**
     * get Z coordinate.
     *
     * @return z coordinate
     */
    public Long getZ() {
        return z;
    }
}
