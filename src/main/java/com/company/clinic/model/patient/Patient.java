package com.company.clinic.model.patient;

import com.company.clinic.model.visit.Visit;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameOfAnimal;

    @Enumerated(EnumType.STRING)
    private AnimalType type;

    @Enumerated(EnumType.STRING)
    private AnimalRace race;

    private int age;

    private String nameOfOwner;

    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private Set<Visit> visits;

    public Patient() {
    }

    public Patient(String nameOfAnimal, AnimalType type, AnimalRace race, int age, String nameOfOwner, String email) {
        this.nameOfAnimal = nameOfAnimal;
        this.type = type;
        this.race = race;
        this.age = age;
        this.nameOfOwner = nameOfOwner;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameOfAnimal() {
        return nameOfAnimal;
    }

    public void setNameOfAnimal(String nameOfAnimal) {
        this.nameOfAnimal = nameOfAnimal;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public void setRace(AnimalRace race) {
        this.race = race;
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

    public void setAge(int age) {
        this.age = age;
    }

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return age == patient.age && Objects.equals(nameOfAnimal, patient.nameOfAnimal) && Objects.equals(type, patient.type) && Objects.equals(race, patient.race) && Objects.equals(nameOfOwner, patient.nameOfOwner) && Objects.equals(email, patient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfAnimal, type, race, age, nameOfOwner, email);
    }
}
