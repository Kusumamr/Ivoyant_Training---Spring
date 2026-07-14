package org.example;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(Exception.class)

    public String handleException(Exception e, Model model){
        model.addAttribute("message",e.getMessage());
        return "error";
    }
}
