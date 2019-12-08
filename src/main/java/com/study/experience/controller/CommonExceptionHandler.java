package com.study.experience.controller;

import com.study.experience.exception.IndexFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(1)
public class CommonExceptionHandler {

    private static final String ERROR_404 = "redirect:/404";

    @ExceptionHandler(IndexFileNotFoundException.class)
    public String fileNotFoundException(IndexFileNotFoundException exception) {
        log.info(exception.getMessage());
        log.info("redirect 404 page");
        return ERROR_404;
    }

}