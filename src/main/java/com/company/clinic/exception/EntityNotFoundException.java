package com.company.clinic.exception;

import com.company.clinic.dto.ErrorCode;

public class EntityNotFoundException extends ClinicBaseException {
    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}