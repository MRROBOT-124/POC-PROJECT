package com.realtime.project.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class UniqueConstraintExceptionsHandler {

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<ExceptionFormat> handleUniqueConstraintExceptions(Exception ex) {
        String message = ExceptionUtils.getRootCause(ex).getMessage().split("Detail: ")[1];
        return new ResponseEntity<>(ExceptionFormat.builder().message(message).httpStatus(HttpStatus.BAD_REQUEST).dateTime(ZonedDateTime.now()).build(), HttpStatus.BAD_REQUEST);
    }
}
