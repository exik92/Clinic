package com.company.clinic.command;

import com.company.clinic.validation.doctor.DoctorExistsById;
import com.company.clinic.validation.patient.PatientExistsById;
import com.company.clinic.validation.doctor.DoctorHasFreeTime;
import com.company.clinic.validation.patient.PatientHasFreeTime;
import com.company.clinic.validation.visit.VisitAlreadyExists;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@DoctorHasFreeTime
@PatientHasFreeTime
@VisitAlreadyExists
public class CreateVisitCommand {

    @DoctorExistsById
    private long doctorId;

    @PatientExistsById
    private long patientId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime visitTime;

    public CreateVisitCommand() {
    }

    public CreateVisitCommand(long doctorId, long patientId, LocalDateTime visitTime) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.visitTime = visitTime;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateVisitCommand that = (CreateVisitCommand) o;
        return doctorId == that.doctorId && patientId == that.patientId && Objects.equals(visitTime, that.visitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, patientId, visitTime);
    }
}
