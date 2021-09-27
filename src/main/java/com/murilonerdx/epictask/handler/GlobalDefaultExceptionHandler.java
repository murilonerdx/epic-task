package com.murilonerdx.epictask.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String
    defaultErrorHandler() {
        return "error";
    }
}
