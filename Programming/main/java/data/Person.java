package data;


import constructors.ParameterConstructor;

import java.util.Scanner;

public class Person {
    private String passportID; //Поле может быть null
    private Location location; //Поле не может быть null

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        if (location==null) throw new IllegalArgumentException("location не может быть null");
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "passportID='" + passportID + '\'' +
                ", location=" + location +
                '}';
    }

    public String getPassportID() {

        return passportID;
    }

    public Location getLocation() {
        return location;
    }
}
