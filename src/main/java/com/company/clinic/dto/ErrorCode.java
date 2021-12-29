package com.company.clinic.dto;

public enum ErrorCode {
    USER_ALREADY_EXISTS("Such user already exists"),
    USER_NOT_EXISTS("User not exists"),
    TOKEN_EXPIRED("Token expired"),
    EMAIL_SENDING_ERROR("There was an error sending the email"),
    ENTITY_NOT_FOUND("Entity not found"),

    DOCTOR_FIRST_NAME_EMPTY("Doctor first name cannot be empty"),
    DOCTOR_LAST_NAME_EMPTY("Doctor last name cannot be empty"),
    DOCTOR_ALREADY_EXISTS("Doctor with NIP exists"),
    DOCTOR_ALREADY_FIRED("Doctor is no longer an employee of our Clinic"),
    DOCTOR_HOURLY_RATE_NEGATIVE("Hourly rate should be greater or equal 0"),
    DOCTOR_NIP_NEGATIVE("NIP cannot be negative"),

    PATIENT_EMAIL_EMPTY("Email cannot be empty"),
    PATIENT_ALREADY_EXISTS("Patient with email exists"),
    PATIENT_AGE_NOT_POSITIVE("Age should be positive"),
    PATIENT_NAME_EMPTY("Patient name cannot be empty"),
    PATIENT_ANIMAL_NAME_EMPTY("Animal name cannot be empty"),

    VISIT_ERROR("Visit error"),
    VISIT_USER_DATE_ERROR("User has another visit, invalid visit date"),
    VISIT_STATE_ERROR("Invalid visit state"),
    VALIDATION_ERROR("Validation error, check your input"),

    UNKNOWN("");


    private final String name;

    ErrorCode(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
