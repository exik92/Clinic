package com.company.clinic.validation.doctor;

import com.company.clinic.repository.DoctorRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class DoctorExistsByIdValidator implements ConstraintValidator<DoctorExistsById, Long> {

    private final DoctorRepository repository;

    public DoctorExistsByIdValidator(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return repository.existsById(value);
    }
}
