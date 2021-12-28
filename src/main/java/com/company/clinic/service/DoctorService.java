package com.company.clinic.service;

import com.company.clinic.command.CreateDoctorCommand;
import com.company.clinic.dto.ErrorCode;
import com.company.clinic.exception.EntityAlreadyExistsException;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctorById(long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not active doctor found with id: " + id));
    }

    @Transactional
    public Doctor addDoctor(CreateDoctorCommand createDoctorCommand) {
        return Optional.of(createDoctorCommand)
                .map(CreateDoctorCommand::getNip)
                .map(doctorRepository::findByNip)
                .filter(opt -> !opt.isPresent())
                .map(opt -> new Doctor(
                        createDoctorCommand.getFirstName(),
                        createDoctorCommand.getLastName(),
                        createDoctorCommand.getMedicalSpecialization(),
                        createDoctorCommand.getMedicalAnimalSpecialization(),
                        createDoctorCommand.getHourlyRate(),
                        true,
                        createDoctorCommand.getNip()
                ))
                .map(doctorRepository::save)
                .orElseThrow(() -> new EntityAlreadyExistsException(
                        "Doctor with this NIP already exists", ErrorCode.DOCTOR_ALREADY_EXISTS));
    }

    public Page<Doctor> findAll(Pageable paging) {
        return doctorRepository.findDoctorsByActive(true, paging);

//        return doctorRepository.findAll(paging)
//                .stream()
//                .filter(Doctor::isActive)
//                .collect(Collectors.toList());
    }

    @Transactional
    public void fireDoctor(long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not active doctor found with id: " + id));
        doctor.setActive(false);
    }
}
