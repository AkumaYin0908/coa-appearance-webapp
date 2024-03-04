package com.coa.exceptions;

public class LeaderNotFoundException extends RuntimeException{

    public LeaderNotFoundException(String message) {
        super(message);
    }
}
