package com.mash.noteapi.exceptions;

public class WrongEmailOrPasswordException extends RuntimeException {
    public WrongEmailOrPasswordException(String message) {
        super(message);
    }
}