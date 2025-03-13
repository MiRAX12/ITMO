package constructors;

import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import data.Location;
import utility.BuildingRequest;

import java.util.Objects;


public class LocationBuilder {

    private static BuildingRequest<Float> askParameterX() {
        return new BuildingRequest<>(new FloatParser(), Objects::nonNull,
                "Введите координату локации Х. Пустая строка не допускается: ");
    }

    private static BuildingRequest<Float> askParameterY() {
        return new BuildingRequest<>(new FloatParser(), Objects::nonNull,
                "Введите координату локации Y. Пустая строка не допускается: ");
    }

    private static BuildingRequest<Long> askParameterZ() {
        return new BuildingRequest<>(new LongParser(), Objects::nonNull,
                "Введите координату локации Z. Пустая строка не допускается: ");
    }

    public static Location build(){
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        Location.Builder builder = new Location.Builder();
        builder.x(parameterConstructor.askParameter(askParameterX()));
        builder.y(parameterConstructor.askParameter(askParameterY()));
        builder.z(parameterConstructor.askParameter(askParameterZ()));
        return builder.build();
    }
}
