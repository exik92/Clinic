package com.company.clinic.validation.visit;

import com.company.clinic.command.VisitActionCommand;
import com.company.clinic.model.visit.Visit;
import com.company.clinic.model.visit.VisitStatus;
import com.company.clinic.repository.VisitRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

@Component
class VisitExistsAndHasProperStatusValidator implements ConstraintValidator<VisitExistsAndHasProperStatus, VisitActionCommand> {

    private final VisitRepository visitRepository;

    public VisitExistsAndHasProperStatusValidator(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public boolean isValid(VisitActionCommand value, ConstraintValidatorContext context) {
        Predicate<Visit> visitStatusPredicate;

        switch (value.getActionType()) {
            case CONFIRM:
                visitStatusPredicate = visit -> visit.getStatus().equals(VisitStatus.CREATED);
                break;
            case CANCEL:
                visitStatusPredicate = visit -> visit.getStatus().equals(VisitStatus.CONFIRMED);
                break;
            default:
                visitStatusPredicate = visit -> false;
        }

        return visitRepository.findById(value.getVisitId())
                .filter(visitStatusPredicate)
                .isPresent();
    }

}
