package data;


import java.util.Scanner;

public class Person implements Validatable {
    private String passportID; //Поле может быть null
    private Location location; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public String getPassportID() {

        return passportID;
    }

    public Location getLocation() {
        return location;
    }
}
