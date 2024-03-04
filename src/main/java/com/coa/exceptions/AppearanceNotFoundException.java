package com.coa.exceptions;

public class AppearanceNotFoundException extends RuntimeException{

    public AppearanceNotFoundException(String message) {
        super(message);
    }
}
