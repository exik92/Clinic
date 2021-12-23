package com.company.clinic.dto;

import java.util.Objects;

public class PatientDto {

    private long id;

    private String nameOfAnimal;

    private String type;

    private String race;

    private int age;

    private String nameOfOwner;

    private String email;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNameOfAnimal() {
        return nameOfAnimal;
    }

    public void setNameOfAnimal(String nameOfAnimal) {
        this.nameOfAnimal = nameOfAnimal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
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
        PatientDto that = (PatientDto) o;
        return age == that.age && Objects.equals(nameOfAnimal, that.nameOfAnimal) && Objects.equals(type, that.type) && Objects.equals(race, that.race) && Objects.equals(nameOfOwner, that.nameOfOwner) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfAnimal, type, race, age, nameOfOwner, email);
    }
}
