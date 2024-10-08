package com.coa.exceptions.rest;

import com.coa.payload.response.APIResponse;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError)objectError).getField();
            String message = objectError.getDefaultMessage();
            response.put(fieldName, message);
        });

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<APIResponse> handleAlreadyExistsException(AlreadyExistException ex){
        return new ResponseEntity<>(new APIResponse(ex.getMessage(),false), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<APIResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        String message = "error";
        if(ex.getErrorCode() == 1451){
            message = "Unable to delete because this item is associated with other entities";
        }
        return new ResponseEntity<>(new APIResponse(message,false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NonUniqueResultException.class, IncorrectResultSizeDataAccessException.class})
    public ResponseEntity<APIResponse> handleNonUniqueResultException(){
        return new ResponseEntity<>(new APIResponse("Error occured due to duplicate entries! Find duplicate entries and delete/remove one of them!",false),HttpStatus.BAD_REQUEST);
    }


}
