package com.company.clinic.validation.patient;

import com.company.clinic.repository.PatientRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class PatientAlreadyExistsByEmailValidator implements ConstraintValidator<PatientAlreadyExistsByEmail, String> {

    private final PatientRepository repository;

    public PatientAlreadyExistsByEmailValidator(PatientRepository repository) {
        this.repository = repository;
    }

    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return !repository.existsByEmail(email);
    }
}
