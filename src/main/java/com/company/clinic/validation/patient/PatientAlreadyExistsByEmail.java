package com.company.clinic.validation.patient;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PatientAlreadyExistsByEmailValidator.class)
public @interface PatientAlreadyExistsByEmail {
    String message() default "PATIENT_ALREADY_EXISTS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
