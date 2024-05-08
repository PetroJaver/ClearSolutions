package org.clearsolutions.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.clearsolutions.restapi.validation.constraint.BirthDateRangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateRangeValidator.class)
public @interface ValidBirthDateRange {
    String message() default "Birth day range is invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
