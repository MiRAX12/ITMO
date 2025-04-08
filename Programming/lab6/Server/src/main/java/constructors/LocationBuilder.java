package constructors;

import constructors.parsers.FloatParser;
import constructors.parsers.LongParser;
import model.Location;
import model.Worker;
import utility.BuildingRequest;

import java.util.Objects;

/**
 * Class for building {@link Location} instance
 */
public class LocationBuilder {

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<Float> askParameterX() {
        return new BuildingRequest<>(new FloatParser(), Objects::nonNull,
                "Введите координату локации Х. Пустая строка не допускается: ");
    }

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<Float> askParameterY() {
        return new BuildingRequest<>(new FloatParser(), Objects::nonNull,
                "Введите координату локации Y. Пустая строка не допускается: ");
    }

    /**
     * Creates {@link BuildingRequest} which contains parameters used to
     * ask and create appropriate field
     *
     * @return {@link BuildingRequest} with building parameters
     * @see BuildingRequest
     */
    private static BuildingRequest<Long> askParameterZ() {
        return new BuildingRequest<>(new LongParser(), Objects::nonNull,
                "Введите координату локации Z. Пустая строка не допускается: ");
    }

    /**
     * Uses {@link ParameterConstructor} class to ask parameters from input
     * and build an instance of Location
     *
     * @return instance of {@link Location}
     * @see ParameterConstructor
     */
    public static Location build(){
        ParameterConstructor parameterConstructor = new ParameterConstructor();
        Location.Builder builder = new Location.Builder();
        builder.x(parameterConstructor.readParameter(askParameterX()));
        builder.y(parameterConstructor.readParameter(askParameterY()));
        builder.z(parameterConstructor.readParameter(askParameterZ()));
        return builder.build();
    }
}
