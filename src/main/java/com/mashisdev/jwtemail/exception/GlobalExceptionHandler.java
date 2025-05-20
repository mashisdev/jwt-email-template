package com.mashisdev.jwtemail.exception;

import com.mashisdev.jwtemail.exception.auth.user.EmailAlreadyExistsException;
import com.mashisdev.jwtemail.exception.auth.user.UserNotFoundException;
import com.mashisdev.jwtemail.exception.auth.user.UsernameAlreadyExistsException;
import com.mashisdev.jwtemail.exception.auth.user.WrongEmailOrPasswordException;
import com.mashisdev.jwtemail.exception.auth.verification.*;
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

    // User not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("user", ex.getMessage()));
    }

    // Registration
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("email", ex.getMessage()));
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("username", ex.getMessage()));
    }

    // Login
    @ExceptionHandler(WrongEmailOrPasswordException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(WrongEmailOrPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("user", ex.getMessage()));
    }
    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotVerified(AccountNotVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("verification", ex.getMessage()));
    }

    // Verification code
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<Map<String, String>> handleInvalidVerificationCode(InvalidVerificationCodeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("verification", ex.getMessage()));
    }
    @ExceptionHandler(VerificationCodeExpiredException.class)
    public ResponseEntity<Map<String, String>> handleInvalidVerificationCode(VerificationCodeExpiredException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(Collections.singletonMap("verification", ex.getMessage()));
    }
    @ExceptionHandler(AccountAlreadyVerifiedException.class)
    public ResponseEntity<Map<String, String>> handleAccountAlreadyVerified(AccountAlreadyVerifiedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("verification", ex.getMessage()));
    }
    @ExceptionHandler(VerificationCodeStillValidException.class)
    public ResponseEntity<Map<String, String>> handleVerificationCodeStillValid(VerificationCodeStillValidException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("verification", ex.getMessage()));
    }

    // Method Not Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(ex.getStatusCode()).body(errors);
    }

}

