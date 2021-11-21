package com.epam.learn.exception;

import org.springframework.http.HttpStatus;

public class EventNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public EventNotFoundException(int id){
        super("Event not found with id = " + id);

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}