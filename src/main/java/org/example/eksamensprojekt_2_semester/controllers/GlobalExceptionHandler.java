package org.example.eksamensprojekt_2_semester.controllers;

import org.example.eksamensprojekt_2_semester.models.exceptions.DeckNotFoundException;
import org.example.eksamensprojekt_2_semester.models.exceptions.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/pages/auth/error";
    }

    @ExceptionHandler(SecurityException.class)
    public String handleSecurityException(SecurityException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/pages/auth/error";
    }

    @ExceptionHandler(DeckNotFoundException.class)
    public String handleDeckNotFoundException(DeckNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/pages/auth/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/pages/auth/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/pages/auth/error";
    }
}
