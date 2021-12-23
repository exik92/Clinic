package com.company.clinic.validation.patient;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatientExistsByIdValidator.class)
public @interface PatientExistsById {
    String message() default "Patient not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

