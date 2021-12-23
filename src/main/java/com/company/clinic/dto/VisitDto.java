package com.company.clinic.dto;

import com.company.clinic.model.visit.VisitStatus;

import java.time.LocalDateTime;

public class VisitDto {

    private long id;

    private DoctorDto doctor;

    private PatientDto patient;

    private LocalDateTime visitTime;

    private VisitStatus status;

    public void setId(long id) {
        this.id = id;
    }

    public void setDoctor(DoctorDto doctor) {
        this.doctor = doctor;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public DoctorDto getDoctor() {
        return doctor;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public VisitStatus getStatus() {
        return status;
    }
}
