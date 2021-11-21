package com.epam.learn.exception;

import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public TicketNotFoundException(int id){
        super("Ticket not found with id = " + id);

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
