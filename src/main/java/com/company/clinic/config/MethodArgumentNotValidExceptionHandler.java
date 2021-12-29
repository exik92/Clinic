package com.company.clinic.config;

import com.company.clinic.dto.ErrorCode;
import com.company.clinic.dto.ErrorDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static com.company.clinic.dto.ErrorCode.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDto>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ErrorDto> valErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    try {
                        return new ErrorDto(
                                ex.getClass().getName(),
                                ErrorCode.valueOf(error.getDefaultMessage()).getName(),
                                ErrorCode.valueOf(error.getDefaultMessage()));
                    } catch (IllegalArgumentException exc) {
                        return new ErrorDto(
                                ex.getClass().getName(),
                                error.getDefaultMessage(),
                                VALIDATION_ERROR);
                    }
                }).collect(Collectors.toList());
        return new ResponseEntity<>(valErrors, BAD_REQUEST);
    }

}
