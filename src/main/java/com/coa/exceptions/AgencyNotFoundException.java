package com.coa.exceptions;

public class AgencyNotFoundException extends RuntimeException{

    public AgencyNotFoundException(String message) {
        super(message);
    }
}
