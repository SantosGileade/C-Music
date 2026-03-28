package com.weslley.c_music.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUse(EntityInUseException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflito de integridade");
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}