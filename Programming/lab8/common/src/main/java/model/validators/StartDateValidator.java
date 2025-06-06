package model.validators;

import java.time.LocalDateTime;

/**
 * Implementation of validator for Population.
 *
 * @since 2.0
 * @author boris
 */
public class StartDateValidator implements Validator<LocalDateTime> {
    public String getDescr() {
        return "Введите дату и время окончания в формате 'yyyy-MM-dd HH:mm:ss z' (например, '2023-10-05 14:30:00 UTC)";
    }
    @Override
    public boolean validate(LocalDateTime value) {
        return value != null;
    }
}
