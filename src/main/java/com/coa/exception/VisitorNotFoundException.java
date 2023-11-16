package com.coa.exception;

public class VisitorNotFoundException extends Exception{
    public VisitorNotFoundException(String message, Throwable cause){
        super(message,cause);
    }

    public VisitorNotFoundException(Throwable cause){
        super(cause);
    }
}
