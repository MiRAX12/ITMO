package common.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Coordinates class.
 */
public class Coordinates implements Serializable {

    /**
     * X coordinate.
     *
     * @constraint  Value must be <603.0
     */
    @DecimalMax(value = "603.0", message = "Координата х не может быть больше 603")
    private float x; //Максимальное значение поля: 603

    /**
     * Y coordinate.
     *
     * @constraint  Can't be null
     */
    @NotNull(message = "Координата у не может быть null")
    private Long y; //Поле не может быть null

    /**
     * set X coordinate.
     *
     * @param x X coordinate.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * set Y coordinate.
     *
     * @param y Y coordinate.
     */
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

    /**
     * A Builder's pattern
     */
    public static class Builder {
        private float x = 0;
        private Long y = null;

        /**
         * @param x - a x coordinate
         *
         * @return - builder's object of written value
         */
        public Builder x(float x) {
            this.x = x;
            return this;
        }

        /**
         * @param y - a x coordinate
         *
         * @return - builder's object of written value
         */
        public Builder y(Long y) {
            this.y = y;
            return this;
        }

        /**
         * Build a Coordinates instance
         *
         * @return {@link Coordinates} instance
         */
        public Coordinates build() {
            Coordinates coordinates = new Coordinates();
            coordinates.y = this.y;
            coordinates.x = this.x;

            return coordinates;
        }
    }

    /**
     * get X coordinate.
     *
     * @return x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * get X coordinate.
     *
     * @return x coordinate
     */
    public Long getY() {
        return y;
    }
}
