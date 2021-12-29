package com.company.clinic.dto;

import com.company.clinic.model.doctor.MedicalAnimalSpecialization;
import com.company.clinic.model.doctor.MedicalSpecialization;

import java.util.Objects;

public class DoctorDto {

    private long id;

    private String firstName;

    private String lastName;

    private MedicalSpecialization medicalSpecialization;

    private MedicalAnimalSpecialization medicalAnimalSpecialization;

    private int hourlyRate;

    private Long nip;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MedicalSpecialization getMedicalSpecialization() {
        return medicalSpecialization;
    }

    public void setMedicalSpecialization(MedicalSpecialization medicalSpecialization) {
        this.medicalSpecialization = medicalSpecialization;
    }

    public MedicalAnimalSpecialization getMedicalAnimalSpecialization() {
        return medicalAnimalSpecialization;
    }

    public void setMedicalAnimalSpecialization(MedicalAnimalSpecialization medicalAnimalSpecialization) {
        this.medicalAnimalSpecialization = medicalAnimalSpecialization;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public long getNip() {
        return nip;
    }

    public void setNip(long nip) {
        this.nip = nip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDto that = (DoctorDto) o;
        return hourlyRate == that.hourlyRate && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && medicalSpecialization == that.medicalSpecialization && medicalAnimalSpecialization == that.medicalAnimalSpecialization && Objects.equals(nip, that.nip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, medicalSpecialization, medicalAnimalSpecialization, hourlyRate, nip);
    }

    @Override
    public String toString() {
        return "DoctorDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", medicalSpecialization=" + medicalSpecialization +
                ", medicalAnimalSpecialization=" + medicalAnimalSpecialization +
                ", hourlyRate=" + hourlyRate +
                ", nip=" + nip +
                '}';
    }
}