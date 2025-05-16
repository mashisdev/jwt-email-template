package com.mashisdev.jwtemail.exception.auth.verification;

public class AccountNotVerifiedException extends RuntimeException {
    public AccountNotVerifiedException(String message) {
        super(message);
    }
}
