package org.clearsolutions.restapi.validation.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.clearsolutions.restapi.dto.GetUsersParams;
import org.clearsolutions.restapi.validation.ValidBirthDateRange;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;

@Lazy
@Component
public class BirthDateRangeValidator implements ConstraintValidator<ValidBirthDateRange, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        GetUsersParams getUsersParams;

        try {
            getUsersParams = (GetUsersParams) o;
        } catch (ClassCastException e) {
            return true;
        }

        return isRangeValid(getUsersParams.getBirthDateFrom(), getUsersParams.getBirthDateTo());
    }

    private static boolean isRangeValid(LocalDate from, LocalDate to) {
        return from == null || to == null || from.isBefore(to);
    }
}
