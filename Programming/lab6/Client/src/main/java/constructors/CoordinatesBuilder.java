package constructors;


import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import model.Coordinates;

import java.util.Objects;

/**
 * Class for building {@link Coordinates} instance
 */
public class CoordinatesBuilder {

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<Float> askParameterX() {
        return new BuildingRequest<>(new FloatParser(), x -> x<=603,
                "Введите координату Х, не превышающую 603: ");
    }

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<Long> askParameterY() {
        return new BuildingRequest<>(new LongParser(), Objects::nonNull,
                "Введите координату Y. Пустая строка не допускается: ");
    }

    /**
     * Uses {@link ParameterConstructor} class to ask parameters from input
     * and build an instance of Coordinates
     *
     * @return instance of {@link Coordinates}
     * @see ParameterConstructor
     */
    public static Coordinates buildCoordinates(){
        Coordinates.Builder builder = new Coordinates.Builder();
        builder.x(ParameterConstructor.readParameter(askParameterX()));
        builder.y(ParameterConstructor.readParameter(askParameterY()));
        return builder.build();
    }

}
