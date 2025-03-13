package constructors;

import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import data.Coordinates;

import java.io.IOException;

public class CoordinatesBuilder {

    public static Coordinates build() throws IOException {
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        Coordinates coordinates = new Coordinates();

        coordinates.setX(parameterConstructor.askParameter(new FloatParser(), "Введите координату Х: "));
        coordinates.setY(parameterConstructor.askParameter(new LongParser(),"Введите координату Y: "));
        return coordinates;
    }



}
