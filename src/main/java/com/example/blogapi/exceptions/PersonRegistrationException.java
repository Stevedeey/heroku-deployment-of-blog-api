package com.example.blogapi.exceptions;

public class PersonRegistrationException extends RuntimeException{
    public PersonRegistrationException(String message) {
        super(message);
    }

    public PersonRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
