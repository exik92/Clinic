package com.company.clinic.validation.visit;

import com.company.clinic.model.visit.Visit;
import com.company.clinic.model.visit.VisitStatus;
import com.company.clinic.repository.VisitRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class VisitIsExistsAndHasCreatedStatusValidator implements ConstraintValidator<VisitIsExistsAndHasCreatedStatus, Long> {

    private final VisitRepository visitRepository;

    public VisitIsExistsAndHasCreatedStatusValidator(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public boolean isValid(Long visitId, ConstraintValidatorContext context) {
        return visitRepository.findById(visitId)
                .map(Visit::getStatus)
                .filter(VisitStatus.CREATED::equals)
                .isPresent();
    }
}
