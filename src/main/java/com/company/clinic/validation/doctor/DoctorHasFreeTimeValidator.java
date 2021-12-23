package com.company.clinic.validation.doctor;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.repository.VisitRepository;
import com.company.clinic.util.GetTimeSlotFromLocalDateTime;
import com.company.clinic.util.Pair;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Component
class DoctorHasFreeTimeValidator implements ConstraintValidator<DoctorHasFreeTime, CreateVisitCommand> {

    private final VisitRepository visitRepository;
    private final GetTimeSlotFromLocalDateTime getTimeSlotFromLocalDateTime;

    public DoctorHasFreeTimeValidator(VisitRepository visitRepository, GetTimeSlotFromLocalDateTime getTimeSlotFromLocalDateTime) {
        this.visitRepository = visitRepository;
        this.getTimeSlotFromLocalDateTime = getTimeSlotFromLocalDateTime;
    }

    @Override
    public boolean isValid(CreateVisitCommand value, ConstraintValidatorContext context) {
        long id = value.getDoctorId();
        Pair<LocalDateTime> apply = getTimeSlotFromLocalDateTime.apply(value.getVisitTime());
        return !visitRepository.existsByDoctor_IdAndVisitTimeBetween(id, apply.getMin(), apply.getMax());
    }
}
