package com.company.clinic.validation.doctor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoctorHasFreeTimeValidator.class)
public @interface DoctorHasFreeTime {
    String message () default "Doctor is busy, invalid visit date";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};
}
