package com.mashisdev.jwtemail.exceptions.auth;

public class WrongEmailOrPasswordException extends RuntimeException {
    public WrongEmailOrPasswordException(String message) {
        super(message);
    }
}