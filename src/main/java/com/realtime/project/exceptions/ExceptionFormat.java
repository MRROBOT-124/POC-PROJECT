package com.realtime.project.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ExceptionFormat {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime dateTime;
}
