package com.murilonerdx.epictask.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String
    defaultErrorHandler() {
        return "error";
    }
}
