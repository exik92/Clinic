package com.company.clinic.service;

import com.company.clinic.command.CreateVisitCommand;
import com.company.clinic.command.VisitActionCommand;
import com.company.clinic.exception.EntityNotFoundException;
import com.company.clinic.exception.TokenExpiredException;
import com.company.clinic.exception.VisitException;
import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.model.patient.Patient;
import com.company.clinic.model.visit.Visit;
import com.company.clinic.model.visit.VisitStatus;
import com.company.clinic.model.visit.VisitToken;
import com.company.clinic.repository.DoctorRepository;
import com.company.clinic.repository.PatientRepository;
import com.company.clinic.repository.VisitRepository;
import com.company.clinic.repository.VisitTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VisitService {

    @Value(value = "${token.expire}")
    private int TOKEN_EXPIRE_TIME;

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final VisitTokenRepository visitTokenRepository;
    private final NotificationService notificationService;

    public VisitService(VisitRepository visitRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, VisitTokenRepository visitTokenRepository, NotificationService notificationService) {
        this.visitRepository = visitRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.visitTokenRepository = visitTokenRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Visit createVisit(CreateVisitCommand createVisitCommand) {
        long doctorId = createVisitCommand.getDoctorId();
        long patientId = createVisitCommand.getPatientId();
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("PATIENT", patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("DOCTOR", doctorId));

        visitRepository.findByDoctor_Id(doctorId).forEach(visit -> validateDates(visit, createVisitCommand));

        Visit visit = new Visit(
                doctor,
                patient,
                createVisitCommand.getVisitTime(),
                VisitStatus.CREATED
        );
        visit = visitRepository.saveAndFlush(visit);
        VisitToken token = new VisitToken();
        token.setToken(UUID.randomUUID().toString());
        token.setVisit(visit);
        token.setExpiryDate(LocalDateTime.now().plusHours(TOKEN_EXPIRE_TIME));
        visitTokenRepository.save(token);
        notificationService.sendMailForVisitCreation(patient.getEmail(), visit);
        return visit;
    }

    @Transactional
    public Visit confirm(VisitActionCommand visitActionCommand) {
        VisitToken visitToken = visitTokenRepository.findByVisit_Id(visitActionCommand.getVisitId())
                .orElseThrow(() -> new EntityNotFoundException("VISIT_TOKEN", visitActionCommand.getVisitId()));
        Visit visit = getVisitWithProperAccess(visitToken);
        visit.setStatus(VisitStatus.CONFIRMED);
        notificationService.sendMailForVisitConfirmation(visit.getPatient().getEmail(), visit);
        return visit;
    }

    @Transactional
    public Visit cancel(VisitActionCommand visitActionCommand) {
        VisitToken visitToken = visitTokenRepository.findByVisit_Id(visitActionCommand.getVisitId())
                .orElseThrow(() -> new EntityNotFoundException("VISIT_TOKEN", visitActionCommand.getVisitId()));
        Visit visit = getVisitWithProperAccess(visitToken);
        visit.setStatus(VisitStatus.CANCELLED);
        notificationService.sendMailForVisitCancellation(visit.getPatient().getEmail(), visit);
        return visit;
    }

    public Visit getVisitById(long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("VISIT", id));
    }

    private Visit getVisitWithProperAccess(VisitToken visitToken) {
        checkIfTokenExpired(visitToken);
        return visitRepository.findById(visitToken.getVisit().getId())
                .orElseThrow(() -> new EntityNotFoundException("VISIT", visitToken.getVisit().getId()));
    }

    private void checkIfTokenExpired(VisitToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            visitTokenRepository.delete(token);
            throw new TokenExpiredException("Token expired");
        }
    }

    private void validateDates(Visit visit, CreateVisitCommand createVisitCommand) {
        LocalDate visitDateFromDb = visit.getVisitTime().toLocalDate();
        LocalDate visitDateFromDto = createVisitCommand.getVisitTime().toLocalDate();
        int hourFromDb = visit.getVisitTime().getHour();
        int hourFromDto = createVisitCommand.getVisitTime().getHour();
        if (visitDateFromDb.isEqual(visitDateFromDto) && hourFromDb == hourFromDto) {
            throw new VisitException("Doctor has already visit in this time, try to choose another hour or day");
        }
    }

}
