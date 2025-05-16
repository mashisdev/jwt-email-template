package com.mashisdev.jwtemail.exception.auth.verification;

public class InvalidVerificationCodeException extends RuntimeException {
    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}