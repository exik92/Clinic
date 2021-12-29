package com.company.clinic.validation;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.repository.DoctorRepository;
import com.company.clinic.repository.PatientRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
class UserExistsByIdValidator implements ConstraintValidator<UserExistsById, CreateVisitCommand> {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    UserExistsByIdValidator(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public boolean isValid(CreateVisitCommand cvc, ConstraintValidatorContext context) {
        boolean result = true;
        context.disableDefaultConstraintViolation();

        if (!patientRepository.findById(cvc.getPatientId()).isPresent()) {
            context
                    .buildConstraintViolationWithTemplate("Patient with id: " + cvc.getPatientId() + " does not exist")
                    .addConstraintViolation();
            result = false;
        }
        if (!doctorRepository.findById(cvc.getDoctorId()).isPresent()) {
            context
                    .buildConstraintViolationWithTemplate("Doctor with id: " + cvc.getDoctorId() + " does not exist")
                    .addConstraintViolation();
            result = false;
        }
        return result;
    }
}
