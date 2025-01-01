package ilya.service.linkshortener.exception.controller;

import ilya.service.linkshortener.exception.service.DefaultExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order
@RestControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    private final DefaultExceptionService service;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpectedException(Exception e, HttpServletRequest request) {
        return service.handleDefault(e, request);
    }
}
