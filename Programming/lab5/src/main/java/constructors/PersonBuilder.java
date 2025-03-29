package constructors;

import constructors.parsers.StringParser;
import model.Person;
import utility.BuildingRequest;

import java.util.Scanner;

public class PersonBuilder {

    private static BuildingRequest<String> askPassportId() {
        return new BuildingRequest<>(new StringParser(),
                "Введите ID паспорта. Если параметр отсутствует, оставьте поле пустым: ");
    }

    public static Person build(){
        System.out.print("Вы хотите внести сведения о Person?\n1: да \n2: нет\n->");
        final Scanner consoleRead = new Scanner(System.in);
        boolean next = true;
        do {
            String input = consoleRead.nextLine().trim();
            switch (input) {
                case ("1"):
                    Person.Builder builder = new Person.Builder();
                    ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
                    builder.location(LocationBuilder.build());
                    builder.passportId(parameterConstructor.askParameter(askPassportId()));
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
