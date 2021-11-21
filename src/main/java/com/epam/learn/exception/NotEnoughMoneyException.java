package com.epam.learn.exception;

import org.springframework.http.HttpStatus;

public class NotEnoughMoneyException extends RuntimeException{

    private HttpStatus httpStatus;

    public NotEnoughMoneyException(int id){
        super("Not enough money for userId = " + id);

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
