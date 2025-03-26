package constructors;

import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import data.Coordinates;
import utility.BuildingRequest;

import java.io.IOException;
import java.util.Objects;

public class CoordinatesBuilder {

    private static BuildingRequest<Float> askParameterX() {
        return new BuildingRequest<>(new FloatParser(), x -> x<=603, "Введите координату Х: ");
    }

    private static BuildingRequest<Long> askParameterY() {
        return new BuildingRequest<>(new LongParser(), Objects::nonNull, "Введите координату Х: ");
    }

    public static Coordinates build() throws IOException {
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        Coordinates coordinates = new Coordinates();

        coordinates.setX(parameterConstructor.askParameter(askParameterX()));
        coordinates.setY(parameterConstructor.askParameter(askParameterY()));
        return coordinates;
    }

}
