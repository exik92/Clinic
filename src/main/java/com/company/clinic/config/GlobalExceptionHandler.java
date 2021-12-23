package com.company.clinic.config;

import com.company.clinic.dto.ErrorCode;
import com.company.clinic.dto.ErrorDto;
import com.company.clinic.exception.EntityAlreadyExistsException;
import com.company.clinic.exception.DoctorAlreadyFiredException;
import com.company.clinic.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorAlreadyFiredException.class)
    public ResponseEntity<ErrorDto> handleDoctorAlreadyFired(DoctorAlreadyFiredException e) {
        return new ResponseEntity<>(new ErrorDto(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleDoctorAlreadyExists(EntityAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorDto(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnexpected(Exception e) {
        return new ResponseEntity<>(new ErrorDto(e.getClass().getName(), e.getMessage(), ErrorCode.UNKNOWN), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(Exception e) {
        return new ResponseEntity<>(new ErrorDto(e.getClass().getName(), e.getMessage(), ErrorCode.VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }
}
