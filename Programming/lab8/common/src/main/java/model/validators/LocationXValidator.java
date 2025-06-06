package model.validators;

/**
 * Implementation of validator for Area
 *
 *  @since 2.0
 *  @author boris
 */
public class LocationXValidator implements Validator<Float> {
    public String getDescr() {
        return "Constraints: can't be empty while PassportId isn't empty";
    }
    @Override
    public boolean validate(Float value) {
        return value != null && value < Float.MAX_VALUE;
    }
}
