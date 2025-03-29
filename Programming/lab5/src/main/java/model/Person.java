package model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class Person {
    private String passportID; //Поле может быть null
    @NotNull(message = "location не может быть null")
    @Valid
    private Location location; //Поле не может быть null

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "passportID='" + passportID + '\'' +
                ", location=" + location +
                '}';
    }

    public static class Builder {
        private String passportId = null;
        private Location location = null;

        public Builder passportId(String passportId) {
            this.passportId = passportId;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }


        public Person build() {
            Person person = new Person();
            person.passportID = this.passportId;
            person.location = this.location;
            return person;
        }
    }

    public String getPassportID() {
        return passportID;
    }

    public Location getLocation() {
        return location;
    }
}
