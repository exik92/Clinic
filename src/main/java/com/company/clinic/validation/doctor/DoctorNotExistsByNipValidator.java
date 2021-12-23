package com.company.clinic.validation.doctor;

import com.company.clinic.repository.DoctorRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class DoctorNotExistsByNipValidator implements ConstraintValidator<DoctorNotExistsByNip, Long> {

    private final DoctorRepository repository;

    public DoctorNotExistsByNipValidator(DoctorRepository repository) {
        this.repository = repository;
    }

    public boolean isValid(Long nip, ConstraintValidatorContext cxt) {
        return !repository.existsByNip(nip);
    }
}
