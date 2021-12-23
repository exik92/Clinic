package com.company.clinic.exception;

import com.company.clinic.dto.ErrorCode;

public class EmailSendException extends ClinicBaseException {
    public EmailSendException(String message) {
        super(message, ErrorCode.EMAIL_SENDING_ERROR);
    }
}