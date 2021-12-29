package com.company.clinic.validation.doctor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoctorNotExistsByNipValidator.class)
public @interface DoctorNotExistsByNip {
    String message() default "DOCTOR_ALREADY_EXISTS";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

