package com.company.clinic.dto;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ErrorCode {
    DOCTOR_ALREADY_EXISTS("Doctor with NIP exists"),
    DOCTOR_NOT_EXISTS("Doctor not exists"),
    DOCTOR_ALREADY_FIRED("Doctor is no longer an employee of our Clinic"),
    EMAIL_SENDING_ERROR("There was an error sending the email"),
    ENTITY_NOT_FOUND("Entity not found"),
    PATIENT_ALREADY_EXISTS("Patient with email exists"),
    PATIENT_NOT_EXISTS("Patient not exists"),
    TOKEN_EXPIRED("Token expired"),
    USER_ALREADY_EXISTS("Such user already exists"),
    VISIT_ERROR("Visit error"),
    VISIT_DOCTOR_DATE_ERROR("Doctor is busy, invalid visit date"),
    VISIT_PATIENT_DATE_ERROR("Patient has another visit, invalid visit date"),
    VISIT_DUPLICATE_ERROR("Visit Already Exists"),
    VISIT_STATE_ERROR("Invalid visit state"),
    VALIDATION_ERROR("Validation error, check your input"),
    UNKNOWN("");


    private final String name;

    private static final Map<String, ErrorCode> ENUM_MAP;

    ErrorCode(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    static {
        ENUM_MAP = Stream.of(values()).collect(Collectors.toMap(ErrorCode::getName, Function.identity()));
    }

    public static ErrorCode getCodeFromValue(String givenName) {
        return ENUM_MAP.getOrDefault(givenName, VALIDATION_ERROR);
    }


}
