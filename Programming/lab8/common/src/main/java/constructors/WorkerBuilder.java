package constructors;

import constructors.parsers.*;
import model.Status;
import model.Worker;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class for building {@link Worker} instance
 */
public class WorkerBuilder {

    /**
     * Uses {@link ParameterConstructor} class to ask parameters from input
     * and build an instance of Worker
     *
     * @return instance of {@link Worker}
     * @see ParameterConstructor
     */
    public static Worker build() {
        Worker.Builder builder = new Worker.Builder();
        builder.name(ParameterConstructor.readParameter(askName()));
        builder.coordinates(CoordinatesBuilder.buildCoordinates());
        builder.salary(ParameterConstructor.readParameter(askSalary()));
        builder.startDate(ParameterConstructor.readParameter(askLocalDateTime()));
        try {
            builder.endDate(ParameterConstructor.readParameter(askZonedDateTime()));
        } catch (DateTimeParseException e) {

        }
        builder.status(askStatus());
        builder.person(PersonBuilder.build());
        return builder.build();
    }


    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<String> askName() {
        return new BuildingRequest<>(new StringParser(), x -> x != null && !x.isEmpty(),
                "Введите имя. Пустая строка не допускается: ");
    }

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<Float> askSalary() {
        return new BuildingRequest<>(new FloatParser(), x -> x > 0,
                "Введите зарплату. Значение должно быть больше 0: ");
    }

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<LocalDateTime> askLocalDateTime() {
        return new BuildingRequest<>(new LocalDateTimeParser(),
                Objects::nonNull, "Введите дату и время начала в формате" +
                " 'yyyy-MM-dd HH:mm:ss' (например, '2023-10-05 14:30:00'). Пустая строка не допускается: ");
    }

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<ZonedDateTime> askZonedDateTime() {
        return new BuildingRequest<>(new ZonedDateTimeParser(),
                "Введите дату и время окончания в формате" +
                        " 'yyyy-MM-dd HH:mm:ss z' (например, '2023-10-05 14:30:00 UTC')." +
                        " Если параметр отсутствует, оставьте поле пустым: ");
    }

    /**
     * Method asks status from input and sets it as a worker's status
     *
     * @return {@link Status} status of worker
     */
    private static Status askStatus() {
        Status status = null;
        Map<String, Status> statusMap = new HashMap<>();
        statusMap.put("1", Status.FIRED);
        statusMap.put("2", Status.HIRED);
        statusMap.put("3", Status.RECOMMENDED_FOR_PROMOTION);
        statusMap.put("4", Status.REGULAR);
        statusMap.put("5", Status.PROBATION);
        final Scanner consoleRead = ParameterConstructor.consoleRead;
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
