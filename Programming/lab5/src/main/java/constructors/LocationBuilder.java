package constructors;

import data.Location;



public class LocationBuilder {

    public static Location build(){
        ParameterConstructor parameterConstructor = new ParameterConstructor();
        Location location = new Location();
        location.setX(parameterConstructor.askParameter("FloatParser",
                Float.class, "Введите координату локации Х: "));
        location.setY(parameterConstructor.askParameter("FloatParser",
                Float.class, "Введите координату локации Y: "));
        location.setZ(parameterConstructor.askParameter("LongParser",
                Long.class, "Введите координату локации Z: "));
        return location;
    }
}
