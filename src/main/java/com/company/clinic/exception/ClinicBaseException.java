package com.company.clinic.exception;

import com.company.clinic.dto.ErrorCode;

public class ClinicBaseException extends RuntimeException {
    private final ErrorCode errorCode;

    public ClinicBaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
