package model.validators;

/**
 * Implementation of validator for Area
 *
 *  @since 2.0
 *  @author boris
 */
public class SalaryValidator implements Validator<Float> {
    public String getDescr() {
        return "Constraints: should be greater than 0";
    }
    @Override
    public boolean validate(Float value) {
        return value > 0 && value < Float.MAX_VALUE;
    }
}
