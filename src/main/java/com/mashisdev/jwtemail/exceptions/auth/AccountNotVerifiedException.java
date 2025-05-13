package com.mashisdev.jwtemail.exceptions.auth;

public class AccountNotVerifiedException extends RuntimeException {
    public AccountNotVerifiedException(String message) {
        super(message);
    }
}
