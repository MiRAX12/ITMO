package constructors;

import constructors.parsers.StringParser;
import model.Person;

import java.util.Scanner;

/**
 * Class for building {@link Person} instance
 */
public class PersonBuilder {

    private static BuildingRequest<String> askPassportId() {
        return new BuildingRequest<>(new StringParser(),
                "Введите ID паспорта. Если параметр отсутствует, оставьте поле пустым: ");
    }

    /**
     * Asks if user wants to write info about {@link Person}. If yes,
     * uses {@link ParameterConstructor} to ask input and build Person
     *
     * @return instance of {@link Person}
     * @see ParameterConstructor
     */
    public static Person build(){
        System.out.print("Вы хотите внести сведения о Person?\n1: да \n2: нет\n->");
        final Scanner consoleRead = ParameterConstructor.consoleRead;
        boolean next = true;
        do {
            String input = consoleRead.nextLine().trim();
            switch (input) {
                case ("1"):
                    Person.Builder builder = new Person.Builder();
                    builder.location(LocationBuilder.build());
                    builder.passportId(ParameterConstructor.readParameter(askPassportId()));
                    return builder.build();
                case ("2"):
                    next = false;
                    break;
                default:
                    System.out.print("Вы хотите внести сведения о Person?\n1: да \n2: нет\n->");
            }
        } while (next);
        return null;

    }
}
