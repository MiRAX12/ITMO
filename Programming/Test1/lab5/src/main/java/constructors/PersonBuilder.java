package constructors;

import constructors.parsers.StringParser;
import data.Person;
import utility.BuildingRequest;

import java.util.Objects;
import java.util.Scanner;

public class PersonBuilder {

    private static BuildingRequest<String> askPassportId() {
        return new BuildingRequest<>(new StringParser(),
                "Введите ID паспорта. Если параметр отсутствует, оставьте поле пустым: ");
    }

    public static Person build(){
        System.out.print("Вы хотите внести сведения о Person?\n1: да \n2: нет\n->");
        Person person = null;
        final Scanner consoleRead = new Scanner(System.in);
        switch (consoleRead.nextLine().trim()) {
            case ("1"):
                Person.Builder builder = new Person.Builder();
                ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
                builder.location(LocationBuilder.build());
                builder.passportId(parameterConstructor.askParameter(askPassportId()));
                person = builder.build();
                break;
            case ("2"):
                return null;
        }

        return person;
    }
}
