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

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(RuntimeException ex, WebRequest request){
        String message = "Invalid fields or empty";
        return handleExceptionInternal(ex, message, new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }

}
