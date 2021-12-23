package com.company.clinic.exception;

import com.company.clinic.dto.ErrorCode;

public class DoctorAlreadyFiredException extends ClinicBaseException {
    public DoctorAlreadyFiredException(String message) {
        super(message, ErrorCode.DOCTOR_ALREADY_FIRED);
    }
}