package com.company.clinic.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserExistsByIdValidator.class)
public @interface UserExistsById {
    String message() default "USER_NOT_EXISTS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

