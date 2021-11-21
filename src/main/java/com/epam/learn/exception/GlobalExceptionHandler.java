package com.epam.learn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleIllegalStateException (Model model,IllegalStateException ex) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        model.addAttribute("statusStr", httpStatus.getReasonPhrase());
        model.addAttribute("statusValue", httpStatus.value());
        model.addAttribute("exception", ex);
        return "error";
    }

    @ExceptionHandler
    public String handleUserNotFoundException (Model model, UserNotFoundException ex) {

        HttpStatus httpStatus = ex.getHttpStatus();

        model.addAttribute("statusStr", httpStatus.getReasonPhrase());
        model.addAttribute("statusValue", httpStatus.value());
        model.addAttribute("exception", ex);
        return "error";
    }

    @ExceptionHandler
    public String handleEventNotFoundException (Model model, EventNotFoundException ex) {

        HttpStatus httpStatus = ex.getHttpStatus();

        model.addAttribute("statusStr", httpStatus.getReasonPhrase());
        model.addAttribute("statusValue", httpStatus.value());
        model.addAttribute("exception", ex);
        return "error";
    }

    @ExceptionHandler
    public String handleTicketNotFoundException (Model model, TicketNotFoundException ex) {

        HttpStatus httpStatus = ex.getHttpStatus();

        model.addAttribute("statusStr", httpStatus.getReasonPhrase());
        model.addAttribute("statusValue", httpStatus.value());
        model.addAttribute("exception", ex);
        return "error";
    }

    @ExceptionHandler
    public String handleNotEnoughMoneyException(Model model, NotEnoughMoneyException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();

        model.addAttribute("statusStr", httpStatus.getReasonPhrase());
        model.addAttribute("statusValue", httpStatus.value());
        model.addAttribute("exception", ex);
        return "error";
    }
}
