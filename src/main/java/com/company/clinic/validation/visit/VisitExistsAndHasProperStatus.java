package com.company.clinic.validation.visit;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VisitExistsAndHasProperStatusValidator.class)
public @interface VisitExistsAndHasProperStatus {
    String message() default "VISIT_STATE_ERROR";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


