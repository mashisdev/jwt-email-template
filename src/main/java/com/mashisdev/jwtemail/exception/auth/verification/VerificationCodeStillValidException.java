package com.mashisdev.jwtemail.exception.auth.verification;

public class VerificationCodeStillValidException extends RuntimeException {
    public VerificationCodeStillValidException(String message) {
        super(message);
    }
}
