package com.company.clinic.doctor;

import com.company.clinic.command.CreateDoctorCommand;
import com.company.clinic.exception.EntityAlreadyExistsException;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.model.doctor.MedicalAnimalSpecialization;
import com.company.clinic.model.doctor.MedicalSpecialization;
import com.company.clinic.repository.DoctorRepository;
import com.company.clinic.service.DoctorService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorTests {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    public void addDoctor() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        when(doctorRepository.findByNip(anyLong())).thenReturn(Optional.empty());
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        //when
        doctorService.addDoctor(new CreateDoctorCommand("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, 100231221));

        //then
        verify(doctorRepository).save(doctor);
    }

    @Test
    public void getDoctorById() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));

        //when
        Doctor doctorDto = doctorService.getDoctorById(1);

        //then
        assertThat(doctorDto.getFirstName()).isEqualTo(doctor.getFirstName());
        assertThat(doctorDto.getLastName()).isEqualTo(doctor.getLastName());
        assertThat(doctorDto.getNip()).isEqualTo(doctor.getNip());
        assertThat(doctorDto.getHourlyRate()).isEqualTo(doctor.getHourlyRate());
        assertThat(doctorDto.getMedicalSpecialization()).isEqualTo(doctor.getMedicalSpecialization());
        assertThat(doctorDto.getMedicalAnimalSpecialization()).isEqualTo(doctor.getMedicalAnimalSpecialization());
    }

    @Test
    public void getAllDoctors() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        Page<Doctor> pagedResponse = new PageImpl<>(doctors);
       // when(doctorRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);
        when(doctorRepository.findDoctorsByActive(any(Boolean.class), any(Pageable.class))).thenReturn(pagedResponse);
        //when
        List<Doctor> doctorsDto = doctorService.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "lastName")).getContent();

        //then
        assertThat(doctors.size()).isEqualTo(doctorsDto.size());
        assertThat(doctorsDto.get(0).getFirstName()).isEqualTo(doctor.getFirstName());
        assertThat(doctorsDto.get(0).getLastName()).isEqualTo(doctor.getLastName());
        assertThat(doctorsDto.get(0).getNip()).isEqualTo(doctor.getNip());
        assertThat(doctorsDto.get(0).getHourlyRate()).isEqualTo(doctor.getHourlyRate());
        assertThat(doctorsDto.get(0).getMedicalSpecialization()).isEqualTo(doctor.getMedicalSpecialization());
        assertThat(doctorsDto.get(0).getMedicalAnimalSpecialization()).isEqualTo(doctor.getMedicalAnimalSpecialization());
    }

    @Test
    public void fireDoctor() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));

        //when
        doctorService.fireDoctor(1);

        //then
        verify(doctorRepository).findById(1L);
    }

    @Test
    public void throwExceptionWhenGettingDoctorById() {
        //given
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException throwable = (EntityNotFoundException) catchThrowable(() -> doctorService.getDoctorById(1));

        //then
        assertTrue(throwable.getMessage().contains("No DOCTOR found with id: 1"));
    }

    @Test
    public void throwExceptionWhenGettingFiringAlreadyFiredDoctor() {
        //given
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException throwable = (EntityNotFoundException) catchThrowable(() -> doctorService.fireDoctor(1));

        //then
        assertTrue(throwable.getMessage().contains("No DOCTOR found with id: 1"));
    }

    @Test
    public void throwExceptionWhenAddingDoctorWithTheSameNip() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        when(doctorRepository.findByNip(anyLong())).thenReturn(Optional.of(doctor));

        //when
        EntityAlreadyExistsException throwable = (EntityAlreadyExistsException) catchThrowable(() -> doctorService.addDoctor(
                new CreateDoctorCommand("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, 100231221)
        ));

        //then
        assertTrue(throwable.getMessage().contains("Doctor with this NIP already exists"));
    }
}