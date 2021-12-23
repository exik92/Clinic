package com.company.clinic.model.doctor;

import com.company.clinic.model.visit.Visit;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "doctors")
@SQLDelete(sql = "UPDATE doctors SET active = false WHERE id=?")
@Where(clause = "active=true")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private MedicalSpecialization medicalSpecialization;

    @Enumerated(EnumType.STRING)
    private MedicalAnimalSpecialization medicalAnimalSpecialization;

    private int hourlyRate;

    private long nip;

    private Boolean active = Boolean.TRUE;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
    private Set<Visit> visits;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, MedicalSpecialization medicalSpecialization, MedicalAnimalSpecialization medicalAnimalSpecialization, int hourlyRate, boolean active, long nip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.medicalSpecialization = medicalSpecialization;
        this.medicalAnimalSpecialization = medicalAnimalSpecialization;
        this.hourlyRate = hourlyRate;
        this.active = active;
        this.nip = nip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        Doctor doctor = (Doctor) o;
        return hourlyRate == doctor.hourlyRate && active == doctor.active && nip == doctor.nip && Objects.equals(firstName, doctor.firstName) && Objects.equals(lastName, doctor.lastName) && medicalSpecialization == doctor.medicalSpecialization && medicalAnimalSpecialization == doctor.medicalAnimalSpecialization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, medicalSpecialization, medicalAnimalSpecialization, hourlyRate, active, nip);
    }
}

