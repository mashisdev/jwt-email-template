package com.mashisdev.jwtemail.exceptions;

import com.mashisdev.jwtemail.exceptions.auth.AccountNotVerifiedException;
import com.mashisdev.jwtemail.exceptions.auth.EmailAlreadyExistsException;
import com.mashisdev.jwtemail.exceptions.auth.UsernameAlreadyExistsException;
import com.mashisdev.jwtemail.exceptions.auth.WrongEmailOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Registration
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("email", ex.getMessage()));
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("username", ex.getMessage()));
    }

    // Login
    @ExceptionHandler(WrongEmailOrPasswordException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(WrongEmailOrPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("user", ex.getMessage()));
    }
    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotVerified(AccountNotVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("verification", ex.getMessage()));
    }

    // Validations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}

