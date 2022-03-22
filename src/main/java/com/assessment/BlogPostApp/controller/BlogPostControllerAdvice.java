package com.assessment.BlogPostApp.controller;

import com.assessment.BlogPostApp.exception.BlogPostNotFoundException;
import com.assessment.BlogPostApp.exception.IncorrectUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class BlogPostControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncorrectUserException.class)
    public ResponseEntity<Object> handleIncorrectUserException(IncorrectUserException e, WebRequest webRequest) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlogPostNotFoundException.class)
    public ResponseEntity<Object> handleBlogPostNotFoundException(BlogPostNotFoundException e, WebRequest webRequest) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
