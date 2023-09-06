package com.webapp.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        // Log the exception for debugging
        ex.printStackTrace();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error"); // Create an error view
        modelAndView.addObject("errorMessage", "An unexpected error occurred.");
        return modelAndView;
    }
}
