package com.company.clinic.model.visit;

import com.company.clinic.model.doctor.Doctor;
import com.company.clinic.model.patient.Patient;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "patientId")
    private Patient patient;

    @NotNull
    private LocalDateTime visitTime;

    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    public Visit() {
    }

    public Visit(@NotNull Doctor doctor, @NotNull Patient patient, @NotNull LocalDateTime visitTime, VisitStatus status) {
        this.doctor = doctor;
        this.patient = patient;
        this.visitTime = visitTime;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(doctor, visit.doctor) && Objects.equals(patient, visit.patient) && Objects.equals(visitTime, visit.visitTime) && status == visit.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, patient, visitTime, status);
    }
}
