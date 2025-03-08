package constructors;

import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import data.Coordinates;
import utility.BuildingRequest;

import java.io.IOException;
import java.util.Objects;

public class CoordinatesBuilder {

    private static BuildingRequest<Float> askParameterX() {
        return new BuildingRequest<>(new FloatParser(), x -> x<=603,
                "Введите координату Х, не превышающую 603: ");
    }

    private static BuildingRequest<Long> askParameterY() {
        return new BuildingRequest<>(new LongParser(), Objects::nonNull,
                "Введите координату Y. Пустая строка не допускается: ");
    }

    public static Coordinates buildCoordinates() throws IOException {
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        Coordinates.Builder builder = new Coordinates.Builder();
        builder.x(parameterConstructor.askParameter(askParameterX()));
        builder.y(parameterConstructor.askParameter(askParameterY()));
        return builder.build();
    }

}
