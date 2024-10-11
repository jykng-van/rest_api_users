package com.jykng.thinkon.usermanagement.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jykng.thinkon.usermanagement.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class UserNotFoundAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex){
        return ex.getMessage();
    }
}
