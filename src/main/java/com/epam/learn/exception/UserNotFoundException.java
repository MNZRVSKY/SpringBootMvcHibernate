package com.epam.learn.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public UserNotFoundException(int id){
        super("User not found with id = " + id);

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}