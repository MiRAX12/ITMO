package Data;

import Interfaces.Validatable;

public class Person implements Validatable {
    private String passportID; //Поле может быть null
    private Location location; //Поле не может быть null

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
