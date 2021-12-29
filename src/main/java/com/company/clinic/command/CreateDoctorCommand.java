package com.company.clinic.command;

import com.company.clinic.model.doctor.MedicalAnimalSpecialization;
import com.company.clinic.model.doctor.MedicalSpecialization;
import com.company.clinic.validation.doctor.DoctorNotExistsByNip;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

public class CreateDoctorCommand {

    @NotEmpty(message = "DOCTOR_FIRST_NAME_EMPTY")
    private String firstName;

    @NotEmpty(message = "DOCTOR_LAST_NAME_EMPTY")
    private String lastName;

    @NotNull
    private MedicalSpecialization medicalSpecialization;

    @NotNull
    private MedicalAnimalSpecialization medicalAnimalSpecialization;

    @PositiveOrZero(message = "DOCTOR_HOURLY_RATE_NEGATIVE")
    private int hourlyRate;

    @Positive(message = "DOCTOR_NIP_NEGATIVE")
    @DoctorNotExistsByNip
    private Long nip;

    public CreateDoctorCommand() {
    }

    public CreateDoctorCommand(String firstName, String lastName, MedicalSpecialization medicalSpecialization, MedicalAnimalSpecialization medicalAnimalSpecialization, int hourlyRate, long nip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalSpecialization = medicalSpecialization;
        this.medicalAnimalSpecialization = medicalAnimalSpecialization;
        this.hourlyRate = hourlyRate;
        this.nip = nip;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public MedicalSpecialization getMedicalSpecialization() {
        return medicalSpecialization;
    }

    public MedicalAnimalSpecialization getMedicalAnimalSpecialization() {
        return medicalAnimalSpecialization;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public long getNip() {
        return nip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateDoctorCommand that = (CreateDoctorCommand) o;
        return hourlyRate == that.hourlyRate && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && medicalSpecialization == that.medicalSpecialization && medicalAnimalSpecialization == that.medicalAnimalSpecialization && Objects.equals(nip, that.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, medicalSpecialization, medicalAnimalSpecialization, hourlyRate, nip);
    }
}