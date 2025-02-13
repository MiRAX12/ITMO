package Data;

import Interfaces.Validatable;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Person implements Validatable {
    private String passportID; //Поле может быть null
    private Location location; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public static class PersonBuilder {
        private String passportID = null;
        private Location location;

        public PersonBuilder () {
            super();
        }

        public void passportID() {
            while (true) {
                System.out.print("ID паспорта: ");
                try {
                    this.passportID = consoleRead.nextLine().trim();
                    break;
                }catch(NoSuchElementException e) { //TODO: Ошибки
                    System.out.println("Ошибка ввода ");
                }
            }
        }

        public void setLocation() {
            this.location = new Location.LocationBuilder().build();
        }

        public Person build() {
            Person person = new Person();
            passportID();
            setLocation();
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
