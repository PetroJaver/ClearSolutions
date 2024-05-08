package org.clearsolutions.restapi.validation.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.clearsolutions.restapi.configuration.properties.ClearSolutionsProperties;
import org.clearsolutions.restapi.validation.ValidBirthDay;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Lazy
public class BirthDayValidator implements ConstraintValidator<ValidBirthDay, Object> {
    private final LocalDate minBirthDay;

    public BirthDayValidator(final ClearSolutionsProperties clearSolutionsProperties) {
        minBirthDay = LocalDate.now().minusYears(clearSolutionsProperties.getUser().getAgeMin());
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if(o != null) {
            LocalDate birthDay;

            try {
                birthDay = (LocalDate) o;
            } catch (Exception e) {
                return false;
            }

            return birthDay.isBefore(minBirthDay);
        }

        return true;
    }
}
