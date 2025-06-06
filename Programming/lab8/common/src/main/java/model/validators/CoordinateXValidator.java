package model.validators;

/**
 * Implementation of validator for CoordinateX
 *
 *  @since 2.0
 *  @author boris
 */
public class CoordinateXValidator implements Validator<Float> {
    public String getDescr() {
        return "Constraints: x <= 603";
    }

    @Override
    public boolean validate(Float value) {return value <= 603F;}
}
