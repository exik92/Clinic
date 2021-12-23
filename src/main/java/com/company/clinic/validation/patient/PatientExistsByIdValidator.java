package com.company.clinic.validation.patient;

import com.company.clinic.repository.PatientRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class PatientExistsByIdValidator implements ConstraintValidator<PatientExistsById, Long> {

    private final PatientRepository repository;

    public PatientExistsByIdValidator(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return repository.existsById(value);
    }
}
