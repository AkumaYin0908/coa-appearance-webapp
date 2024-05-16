package com.coa.exceptions.rest;

import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException {

    private String entityName;
    private String fieldName;


    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String entityName, String fieldName) {
        super(String.format("%s's %s already exists!",entityName, fieldName));
        this.entityName = entityName;
        this.fieldName = fieldName;
    }
}
