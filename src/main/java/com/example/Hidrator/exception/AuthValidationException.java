package com.example.Hidrator.exception;

public class AuthValidationException extends Exception{

    private final String message;
    public AuthValidationException(String message){
        super(message);
        this.message=message;
    }
}
