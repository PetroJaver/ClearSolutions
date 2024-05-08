package org.clearsolutions.restapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.clearsolutions.restapi.validation.constraint.BirthDayValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDayValidator.class)
public @interface ValidBirthDay {
    String message() default "must be older";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
