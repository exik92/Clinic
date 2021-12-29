package com.company.clinic.validation.commons;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserHasFreeTimeValidator.class)
public @interface UserHasFreeTime {
    String message() default "VISIT_USER_DATE_ERROR";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
