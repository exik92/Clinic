package com.company.clinic.patient;

import com.company.clinic.command.CreatePatientCommand;
import com.company.clinic.exception.EntityAlreadyExistsException;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.model.patient.AnimalRace;
import com.company.clinic.model.patient.AnimalType;
import com.company.clinic.model.patient.Patient;
import com.company.clinic.repository.PatientRepository;
import com.company.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientTests {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void addPatient() {
        //given
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        when(patientRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        //when
        patientService.addPatient(new CreatePatientCommand("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com"));

        //then
        verify(patientRepository).save(patient);
    }

    @Test
    public void getPatientById() {
        //given
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));

        //when
        Patient patientDto = patientService.getPatientById(1);

        //then
        assertThat(patientDto.getNameOfAnimal()).isEqualTo(patient.getNameOfAnimal());
        assertThat(patientDto.getAge()).isEqualTo(patient.getAge());
        assertThat(patientDto.getEmail()).isEqualTo(patient.getEmail());
        assertThat(patientDto.getRace()).isEqualTo(patient.getRace());
        assertThat(patientDto.getType()).isEqualTo(patient.getType());
        assertThat(patientDto.getNameOfOwner()).isEqualTo(patient.getNameOfOwner());
    }

    @Test
    public void getAllPatients() {
        //given
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        Page<Patient> pagedResponse = new PageImpl<>(patients);
        when(patientRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        //when
        List<Patient> patientsDto = patientService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "nameOfAnimal"));

        //then
        assertThat(patients.size()).isEqualTo(patientsDto.size());
        assertThat(patientsDto.get(0).getNameOfAnimal()).isEqualTo(patient.getNameOfAnimal());
        assertThat(patientsDto.get(0).getAge()).isEqualTo(patient.getAge());
        assertThat(patientsDto.get(0).getEmail()).isEqualTo(patient.getEmail());
        assertThat(patientsDto.get(0).getRace()).isEqualTo(patient.getRace());
        assertThat(patientsDto.get(0).getType()).isEqualTo(patient.getType());
        assertThat(patientsDto.get(0).getNameOfOwner()).isEqualTo(patient.getNameOfOwner());
    }

    @Test
    public void throwExceptionWhenGettingPatientById() {
        //given
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException throwable = (EntityNotFoundException) catchThrowable(() -> patientService.getPatientById(1));

        //then
        assertTrue(throwable.getMessage().contains("Patient not found"));
    }

    @Test
    public void throwExceptionWhenAddingPatientWithTheSameEmail() {
        //given
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        when(patientRepository.findByEmail(anyString())).thenReturn(Optional.of(patient));

        //when
        EntityAlreadyExistsException throwable = (EntityAlreadyExistsException) catchThrowable(() -> patientService.addPatient(
                new CreatePatientCommand("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com")
        ));

        //then
        assertTrue(throwable.getMessage().contains("Patient with this email already exists"));
    }
}