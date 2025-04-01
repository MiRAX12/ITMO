package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Person class.
 */
public class Person {
    private String passportId; //Поле может быть null
    @NotNull(message = "location не может быть null")
    @Valid
    private Location location; //Поле не может быть null

    /**
     * set passportId
     *
     * @param passportId person's passportId
     */
    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    /**
     * set location
     *
     * @param location person's location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "passportID='" + passportId + '\'' +
                ", location=" + location +
                '}';
    }

    /**
     * A Builder's pattern
     */
    public static class Builder {
        private String passportId = null;
        private Location location = null;

        /**
         * @param passportId - person's passportId
         *
         * @return - builder's object of written value
         */
        public Builder passportId(String passportId) {
            this.passportId = passportId;
            return this;
        }

        /**
         * @param location - person's location
         *
         * @return - builder's object of written value
         */
        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        /**
         * Build a Person instance
         *
         * @return {@link Person} instance
         */
        public Person build() {
            Person person = new Person();
            person.passportId = this.passportId;
            person.location = this.location;
            return person;
        }
    }

    /**
     * get passportId.
     *
     * @return passportId
     */
    public String getPassportId() {
        return passportId;
    }

    /**
     * get location.
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }
}
