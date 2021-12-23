package com.company.clinic.validation.visit;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.repository.VisitRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class VisitAlreadyExistsValidator implements ConstraintValidator<VisitAlreadyExists, CreateVisitCommand> {

    private final VisitRepository visitRepository;

    public VisitAlreadyExistsValidator(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public boolean isValid(CreateVisitCommand value, ConstraintValidatorContext context) {
        return !visitRepository.existsByDoctor_IdAndPatient_IdAndVisitTime(value.getDoctorId(), value.getPatientId(), value.getVisitTime());
    }
}
