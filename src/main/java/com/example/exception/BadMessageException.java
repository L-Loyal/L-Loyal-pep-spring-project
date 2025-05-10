package com.example.exception;

public class BadMessageException extends RuntimeException {
    
    public BadMessageException (String message) {

        super(message);
    }
}
