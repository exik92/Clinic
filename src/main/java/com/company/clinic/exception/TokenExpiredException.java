package com.company.clinic.exception;

import com.company.clinic.dto.ErrorCode;

public class TokenExpiredException extends ClinicBaseException {
    public TokenExpiredException(String message) {
        super(message, ErrorCode.TOKEN_EXPIRED);
    }
}