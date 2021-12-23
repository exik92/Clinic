package com.company.clinic.exception;


import com.company.clinic.dto.ErrorCode;

public class EntityAlreadyExistsException extends ClinicBaseException {
    public EntityAlreadyExistsException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}