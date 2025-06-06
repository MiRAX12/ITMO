package model.validators;

/**
 * Implementation of validator for Area
 *
 *  @since 2.0
 *  @author boris
 */
public class LocationZValidator implements Validator<Long> {
    public String getDescr() {
        return "Constraints: can't be empty while PassportId isn't empty";
    }
    @Override
    public boolean validate(Long value) {
        return value != null && value < Long.MAX_VALUE;
    }
}
