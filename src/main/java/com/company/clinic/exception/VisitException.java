package com.company.clinic.exception;

import com.company.clinic.dto.ErrorCode;

public class VisitException extends ClinicBaseException {
    public VisitException(String message) {
        super(message, ErrorCode.VISIT_ERROR);
    }
}