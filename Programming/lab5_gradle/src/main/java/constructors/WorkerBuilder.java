package constructors;

import constructors.parsers.*;
import data.Status;
import data.Worker;
import utility.BuildingRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class WorkerBuilder {

    public static Worker build(){
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        Worker.Builder builder = new Worker.Builder();

        builder.name(parameterConstructor.askParameter(askName()));
        builder.coordinates(CoordinatesBuilder.buildCoordinates());
        builder.salary(parameterConstructor.askParameter(askSalary()));
        builder.startDate(parameterConstructor.askParameter(askLocalDateTime()));
        builder.endDate(parameterConstructor.askParameter(askZonedDateTime()));
        builder.status(askStatus());
        builder.person(PersonBuilder.build());
        return builder.build();
    }

    private static BuildingRequest<String> askName() {
        return new BuildingRequest<>(new StringParser(), x -> x != null && !x.isEmpty(),
                "Введите имя. Пустая строка не допускается: ");
    }

    private static BuildingRequest<Float> askSalary() {
        return new BuildingRequest<>(new FloatParser(), x -> x > 0,
                "Введите зарплату. Значение должно быть больше 0: ");
    }

    private static BuildingRequest<LocalDateTime> askLocalDateTime() {
        return new BuildingRequest<>(new LocalDateTimeParser(),
                Objects::nonNull, "Введите дату и время начала в формате" +
                " 'yyyy-MM-dd HH:mm:ss' (например, '2023-10-05 14:30:00'). Пустая строка не допускается: ");
    }

    private static BuildingRequest<ZonedDateTime> askZonedDateTime() {
        return new BuildingRequest<>(new ZonedDateTimeParser(),
                "Введите дату и время окончания в формате" +
                " 'yyyy-MM-dd HH:mm:ss z' (например, '2023-10-05 14:30:00 UTC')." +
                " Если параметр отсутствует, оставьте поле пустым: ");
    }

    private static Status askStatus(){
        Status status = null;
        Map<String, Status> statusMap = new HashMap<>();
        statusMap.put("1", Status.FIRED);
        statusMap.put("2", Status.HIRED);
        statusMap.put("3", Status.RECOMMENDED_FOR_PROMOTION);
        statusMap.put("4", Status.REGULAR);
        statusMap.put("5", Status.PROBATION);
        final Scanner consoleRead = new Scanner(System.in);
        while (status == null) {
            System.out.print("Введите статус (1 - FIRED, 2 - HIRED," +
                    " 3 - RECOMMENDED_FOR_PROMOTION, 4 - REGULAR, 5 - PROBATION). Пустая строка не допускается: ");
            String input = consoleRead.nextLine().trim();
            if (input.isEmpty()) {
                break;
            }
            status = statusMap.get(input);
        }
        return status;
    }
}
