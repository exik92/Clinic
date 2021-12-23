package com.company.clinic.validation.patient;

import com.company.clinic.repository.PatientRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class PatientNotExistsByEmailValidator implements ConstraintValidator<PatientNotExistsByEmail, String> {

    private final PatientRepository repository;

    public PatientNotExistsByEmailValidator(PatientRepository repository) {
        this.repository = repository;
    }

    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return !repository.existsByEmail(email);
    }
}
