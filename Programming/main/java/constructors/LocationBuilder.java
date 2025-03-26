package constructors;

import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import data.Location;
import utility.BuildingRequest;

import java.util.Objects;


public class LocationBuilder {

    private static BuildingRequest<Float> askParameterX() {
        return new BuildingRequest<>(new FloatParser(), Objects::nonNull, "Введите координату локации Х: ");
    }

    private static BuildingRequest<Float> askParameterY() {
        return new BuildingRequest<>(new FloatParser(), Objects::nonNull, "Введите координату локации Y: ");
    }

    private static BuildingRequest<Long> askParameterZ() {
        return new BuildingRequest<>(new LongParser(), Objects::nonNull, "Введите координату локации Z: ");
    }

    public static Location build(){
        ParameterConstructor parameterConstructor = ParameterConstructor.getInstance();
        Location location = new Location();
        location.setX(parameterConstructor.askParameter(askParameterX()));
        location.setY(parameterConstructor.askParameter(askParameterY()));
        location.setZ(parameterConstructor.askParameter(askParameterZ()));
        return location;
    }
}
