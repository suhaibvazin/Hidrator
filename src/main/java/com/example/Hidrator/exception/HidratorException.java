package com.example.Hidrator.exception;

public class HidratorException extends Exception{
    String message;
    public HidratorException(String message){
        super(message);
        this.message=message;
    }
}
