package com.company.clinic.visit;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.command.VisitActionCommand;
import com.company.clinic.dto.DoctorDto;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.model.doctor.MedicalAnimalSpecialization;
import com.company.clinic.model.doctor.MedicalSpecialization;
import com.company.clinic.model.patient.AnimalRace;
import com.company.clinic.model.patient.AnimalType;
import com.company.clinic.model.patient.Patient;
import com.company.clinic.model.visit.Visit;
import com.company.clinic.model.visit.VisitAction;
import com.company.clinic.model.visit.VisitStatus;
import com.company.clinic.model.visit.VisitToken;
import com.company.clinic.repository.DoctorRepository;
import com.company.clinic.repository.PatientRepository;
import com.company.clinic.repository.VisitRepository;
import com.company.clinic.repository.VisitTokenRepository;
import com.company.clinic.service.NotificationService;
import com.company.clinic.service.VisitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisitTests {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private VisitTokenRepository visitTokenRepository;
    
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private VisitService visitService;

    @Test
    public void createVisit() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        VisitToken visitToken = new VisitToken(UUID.randomUUID().toString(), new Visit(doctor, patient, LocalDateTime.now(), VisitStatus.CREATED), LocalDateTime.now().plusHours(1));
        CreateVisitCommand cvc = new CreateVisitCommand(1l, 1l, LocalDateTime.now());

        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(visitRepository.saveAndFlush(any(Visit.class))).thenReturn(new Visit(doctor, patient, cvc.getVisitTime(), VisitStatus.CREATED));
        when(visitTokenRepository.save(any(VisitToken.class))).thenReturn(visitToken);
        doNothing().when(notificationService).sendMailForVisitCreation(anyString(), any(Visit.class));

        //when
        visitService.createVisit(cvc);

        //then
        verify(patientRepository).findById(anyLong());
        verify(doctorRepository).findById(anyLong());
        verify(visitRepository).saveAndFlush(any(Visit.class));
        verify(visitRepository).findByDoctor_Id(anyLong());
        verify(visitTokenRepository).save(any(VisitToken.class));
        verify(notificationService).sendMailForVisitCreation(anyString(), any(Visit.class));
    }

    @Test
    public void confirmVisit() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        Visit visit = new Visit(doctor, patient, LocalDateTime.now(), VisitStatus.CREATED);
        String token = UUID.randomUUID().toString();
        VisitToken visitToken = new VisitToken(token, visit, LocalDateTime.now().plusHours(1));
        VisitActionCommand visitActionCommand = new VisitActionCommand(visit.getId(), VisitAction.CONFIRM);

        when(visitTokenRepository.findByVisit_Id(anyLong())).thenReturn(Optional.of(visitToken));
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        //when
        visitService.confirm(visitActionCommand);

        //then
        verify(visitTokenRepository).findByVisit_Id(anyLong());
        verify(visitRepository).findById(anyLong());
    }

    @Test
    public void cancelVisit() {
        //given
        Doctor doctor = new Doctor("John", "Doe", MedicalSpecialization.DERMATOLOGY, MedicalAnimalSpecialization.BIRD, 100, true, 100231221);
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        Visit visit = new Visit(doctor, patient, LocalDateTime.now(), VisitStatus.CREATED);
        String token = UUID.randomUUID().toString();
        VisitToken visitToken = new VisitToken(token, visit, LocalDateTime.now().plusHours(1));
        VisitActionCommand visitActionCommand = new VisitActionCommand(visit.getId(), VisitAction.CANCEL);

        when(visitTokenRepository.findByVisit_Id(anyLong())).thenReturn(Optional.of(visitToken));
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        //when
        visitService.cancel(visitActionCommand);

        //then
        verify(visitTokenRepository).findByVisit_Id(anyLong());
        verify(visitRepository).findById(anyLong());
    }

    @Test
    public void throwExceptionWhenPatientNotFound() {
        //given
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException throwable = (EntityNotFoundException) catchThrowable(() ->
                visitService.createVisit(new CreateVisitCommand(1l, 1l, LocalDateTime.now()))
        );

        //then
        assertTrue(throwable.getMessage().contains("No PATIENT found with id: 1"));
    }

    @Test
    public void throwExceptionWhenDoctorNotFound() {
        //given
        Patient patient = new Patient("Rino", AnimalType.DOG, AnimalRace.DOG_BULLDOG, 2, "John", "john@email.com");
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        EntityNotFoundException throwable = (EntityNotFoundException) catchThrowable(() ->
                visitService.createVisit(new CreateVisitCommand(1l, 1l, LocalDateTime.now()))
        );

        //then
        assertTrue(throwable.getMessage().contains("No DOCTOR found with id: 1"));
    }



    @Test
    public void test() {

        Doctor doctor = new Doctor();
        doctor.setActive(false);
        doctor.setFirstName("janusz");
        doctor.setHourlyRate(23);
        doctor.setId(2);
        doctor.setLastName("nosacz");
        doctor.setMedicalAnimalSpecialization(MedicalAnimalSpecialization.CAT);
        doctor.setMedicalSpecialization(MedicalSpecialization.DERMATOLOGY);

        DoctorDto map = new ModelMapper().map(doctor, DoctorDto.class);
        System.out.println(map);
    }
}
