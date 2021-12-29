package com.company.clinic.command;

import com.company.clinic.model.patient.AnimalRace;
import com.company.clinic.model.patient.AnimalType;
import com.company.clinic.validation.patient.PatientAlreadyExistsByEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class CreatePatientCommand {

    @NotEmpty(message = "PATIENT_ANIMAL_NAME_EMPTY")
    private String nameOfAnimal;

    @NotNull
    private AnimalType type;

    @NotNull
    private AnimalRace race;

    @Positive(message = "PATIENT_AGE_NOT_POSITIVE")
    private int age;

    @NotEmpty(message = "PATIENT_NAME_EMPTY")
    private String nameOfOwner;

    @NotEmpty(message = "PATIENT_EMAIL_EMPTY")
    @PatientAlreadyExistsByEmail
    private String email;

    public CreatePatientCommand() {
    }

    public CreatePatientCommand(String nameOfAnimal, AnimalType type, AnimalRace race, int age, String nameOfOwner, String email) {
        this.nameOfAnimal = nameOfAnimal;
        this.type = type;
        this.race = race;
        this.age = age;
        this.nameOfOwner = nameOfOwner;
        this.email = email;
    }

    public String getNameOfAnimal() {
        return nameOfAnimal;
    }

    public AnimalType getType() {
        return type;
    }

    public AnimalRace getRace() {
        return race;
    }

    public int getAge() {
        return age;
    }

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatePatientCommand that = (CreatePatientCommand) o;
        return age == that.age && Objects.equals(nameOfAnimal, that.nameOfAnimal) && Objects.equals(type, that.type) && Objects.equals(race, that.race) && Objects.equals(nameOfOwner, that.nameOfOwner) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfAnimal, type, race, age, nameOfOwner, email);
    }
}
