package com.company.clinic.validation;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.repository.VisitRepository;
import com.company.clinic.util.GetTimeSlotFromLocalDateTime;
import com.company.clinic.util.Pair;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Component
class UserHasFreeTimeValidator implements ConstraintValidator<UserHasFreeTime, CreateVisitCommand> {

    private final VisitRepository visitRepository;
    private final GetTimeSlotFromLocalDateTime getTimeSlotFromLocalDateTime;

    public UserHasFreeTimeValidator(VisitRepository visitRepository, GetTimeSlotFromLocalDateTime getTimeSlotFromLocalDateTime) {
        this.visitRepository = visitRepository;
        this.getTimeSlotFromLocalDateTime = getTimeSlotFromLocalDateTime;
    }

    @Override
    public boolean isValid(CreateVisitCommand cvc, ConstraintValidatorContext context) {
        long doctorId = cvc.getDoctorId();
        long patientId = cvc.getPatientId();

        boolean result = true;
        Pair<LocalDateTime> apply = getTimeSlotFromLocalDateTime.apply(cvc.getVisitTime());
        context.disableDefaultConstraintViolation();

        if (visitRepository.existsByDoctor_IdAndVisitTimeBetween(doctorId, apply.getMin(), apply.getMax())) {
            context
                    .buildConstraintViolationWithTemplate("Doctor with id: " + doctorId + " has another visit on this time")
                    .addConstraintViolation();
            result = false;
        }
        if (visitRepository.existsByPatient_IdAndVisitTimeBetween(patientId, apply.getMin(), apply.getMax())) {
            context
                    .buildConstraintViolationWithTemplate("Patient with id: " + patientId + " has another visit on this time")
                    .addConstraintViolation();
            result = false;
        }
        return result;
    }
}
