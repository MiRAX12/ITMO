package constructors;

import constructors.parsers.StringParser;
import data.Person;
import utility.BuildingRequest;

import java.util.Objects;

public class PersonBuilder {

    private static BuildingRequest<String> askPassportId() {
        return new BuildingRequest<>(new StringParser(), Objects::nonNull, "Введите ID паспорта: ");
    }

    public static Person build(){
        Person person = new Person();
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        person.setLocation(LocationBuilder.build());
        person.setPassportID(parameterConstructor.askParameter(askPassportId()));
        return person;
    }
}
