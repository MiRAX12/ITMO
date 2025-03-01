package constructors;

import data.Person;

import java.util.Scanner;

public class PersonBuilder {

    public static Person build(){
        Person person = new Person();
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        person.setLocation(LocationBuilder.build());
        person.setPassportID(parameterConstructor.askParameter("StringParser",
                String.class, "Введите ID паспорта: "));
        return person;
    }
}
