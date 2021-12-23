package com.company.clinic.dto;

import com.company.clinic.exception.ClinicBaseException;

import java.util.Objects;

public class ErrorDto {
    private String exception;
    private String exceptionMessage;
    private ErrorCode errorCode;

    public ErrorDto() {
    }

    public ErrorDto(ClinicBaseException e) {
        this(e.getClass().getName(), e.getMessage(), e.getErrorCode());
    }

    public ErrorDto(String exception, String exceptionMessage, ErrorCode errorCode) {
        this.exception = exception;
        this.exceptionMessage = exceptionMessage;
        this.errorCode = errorCode;
    }

    public String getException() {
        return exception;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto that = (ErrorDto) o;
        return Objects.equals(exception, that.exception) && Objects.equals(exceptionMessage, that.exceptionMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exception, exceptionMessage);
    }
}
