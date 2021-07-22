package com.api.blog.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ResponseEntityExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request){
        int index = ex.getMessage().lastIndexOf('.');
        String message = ex.getMessage().substring(index+1);
        return handleExceptionInternal(ex, message, new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }
}
