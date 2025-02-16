package data;

import interfaces.Validatable;


import java.util.Scanner;

import static data.ParameterConstructor.askParameterString;

public class Person implements Validatable {
    private String passportID; //Поле может быть null
    private Location location; //Поле не может быть null
    private static Scanner consoleRead = new Scanner(System.in);

    public static Person build(){
        Person person = new Person();
        person.passportID = askParameterString("Введите ID паспорта: ");
        person.location = Location.build();
        return person;
    }

//    public static class PersonBuilder {
//        private String passportID = null;
//        private Location location;
//
//        public PersonBuilder passportID() {
//            while (true) {
//                System.out.print("ID паспорта: ");
//                try {
//                    this.passportID = consoleRead.nextLine().trim();
//                    break;
//                }catch(NoSuchElementException e) { //TODO: Ошибки
//                    System.out.println("Ошибка ввода ");
//                }
//            }
//            return this;
//        }
//
//        public PersonBuilder setLocation() {
//            this.location = new Location.LocationBuilder().setX().setY().setZ().build();
//            return this;
//        }
//
//        public Person build() {
//            Person person = new Person();
//            person.passportID = passportID;
//            person.location = location;
//            return person;
//        }
//    }

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
