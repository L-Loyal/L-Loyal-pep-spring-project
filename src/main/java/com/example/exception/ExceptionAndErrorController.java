package com.example.exception;

import org.apache.tomcat.websocket.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAndErrorController {

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegistrationException (RegistrationException e) {

        return e.getMessage();
    }

    @ExceptionHandler(DuplicateAccountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateException (DuplicateAccountException e) {

        return e.getMessage();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized (AuthenticationException e) {

        return e.getMessage();
    }

    @ExceptionHandler(BadMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadMessageException (BadMessageException e) {

        return e.getMessage();
    }
}