package constructors;

import constructors.parsers.FloatParser;
import data.Coordinates;

import java.io.IOException;

public class CoordinatesBuilder {

    public static Coordinates build() throws IOException {
        ParameterConstructor parameterConstructor = new ParameterConstructor();
        Coordinates coordinates = new Coordinates();

        coordinates.setX(parameterConstructor.askParameter("FloatParser",
                Float.class, "Введите координату Х: "));
        coordinates.setY(parameterConstructor.askParameter("LongParser",
                Long.class, "Введите координату Y: "));
        return coordinates;
    }

}
