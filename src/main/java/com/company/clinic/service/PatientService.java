package com.company.clinic.service;

import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.dto.ErrorCode;
import com.company.clinic.exception.EntityAlreadyExistsException;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.model.patient.Patient;
import com.company.clinic.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient addPatient(CreatePatientCommand createPatientCommand) {
        return Optional.of(createPatientCommand)
                .map(CreatePatientCommand::getEmail)
                .map(patientRepository::findByEmail)
                .filter(opt -> !opt.isPresent())
                .map(opt -> new Patient(
                        createPatientCommand.getNameOfAnimal(),
                        createPatientCommand.getType(),
                        createPatientCommand.getRace(),
                        createPatientCommand.getAge(),
                        createPatientCommand.getNameOfOwner(),
                        createPatientCommand.getEmail()
                ))
                .map(patientRepository::save)
                .orElseThrow(() -> new EntityAlreadyExistsException("Patient with this email already exists", ErrorCode.PATIENT_ALREADY_EXISTS));
    }

    public Patient getPatientById(long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PATIENT", id));
    }

    public Page<Patient> findAll(Pageable paging) {
        return patientRepository.findAll(paging);
    }
}
