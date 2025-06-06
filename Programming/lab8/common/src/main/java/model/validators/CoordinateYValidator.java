package model.validators;

/**
 * Implementation of validator for CoordinateY
 *
 *  @since 2.0
 *  @author boris
 */
public class CoordinateYValidator implements Validator<Long> {
    public String getDescr() {
        return "Constraints: 0 <= y <= 1000";
    }

    @Override
    public boolean validate(Long value) {return value != null && value < Long.MAX_VALUE;}
}
