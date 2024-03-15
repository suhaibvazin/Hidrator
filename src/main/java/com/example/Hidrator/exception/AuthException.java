package com.example.Hidrator.exception;

import org.springframework.security.core.Authentication;

public class AuthException extends Exception{
    String message;
    public AuthException(String message){
        super(message);
        this.message=message;
    }
}
