package com.company.clinic.validation.patient;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatientHasFreeTimeValidator.class)
public @interface PatientHasFreeTime {
    String message () default "Patient has another visit, invalid visit date";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};
}
