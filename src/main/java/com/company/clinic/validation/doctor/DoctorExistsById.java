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
@Constraint(validatedBy = DoctorExistsByIdValidator.class)
public @interface DoctorExistsById {
    String message() default "Doctor not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

