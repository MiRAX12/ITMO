package Data;

import Interfaces.Validatable;

public class Person implements Validatable {
    private String passportID; //Поле может быть null
    private Location location; //Поле не может быть null

    public static class PersonBuilder {
        private String passportID = null;
        private Location location;

        public PersonBuilder (String passportID) {
            this.location = null;
        }

        public PersonBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.passportID = passportID;
            person.location = location;
            return person;
        }
    }

    @Override
    public boolean validate() {
        if (location == null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person{" +
                "passportID='" + passportID + '\'' +
                ", location=" + location +
                '}';
    }
}
