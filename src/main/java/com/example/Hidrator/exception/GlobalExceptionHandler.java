package com.example.Hidrator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handleAuthException(AuthException ex){
        log.error("Error occurred during authentication"+HttpStatus.BAD_REQUEST.value()+"->"+ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ExceptionResponse("Error occured during authentication"+"->"+ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(AuthValidationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthValidationException(AuthValidationException ex){
        log.error("Error occurred during validation, status:"+HttpStatus.BAD_REQUEST.value()+"->"+ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponse("Error occured during validation"+"->"+ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException ex){
        log.error(ex.getMessage()+":"+HttpStatus.NOT_FOUND.value());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionResponse(ex.getMessage(),HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(MethodArgumentNotValidException ex) {

        log.error(ex.getMessage()+":"+HttpStatus.INTERNAL_SERVER_ERROR.value());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex){
        log.error(ex.getMessage()+":"+HttpStatus.INTERNAL_SERVER_ERROR.value());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
