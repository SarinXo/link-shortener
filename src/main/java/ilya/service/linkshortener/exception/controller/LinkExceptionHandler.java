package ilya.service.linkshortener.exception.controller;

import ilya.service.linkshortener.exception.constant.AdvicePriority;
import ilya.service.linkshortener.exception.dto.ValidationExceptionMessage;
import ilya.service.linkshortener.exception.model.NotFoundException;
import ilya.service.linkshortener.exception.service.LinkExceptionService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_HTML;

@Slf4j
@Order(AdvicePriority.FIRST)
@RestControllerAdvice
@RequiredArgsConstructor
public class LinkExceptionHandler {

    private final LinkExceptionService service;

    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_HTML)
                .body(service.getNotFoundPage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationExceptionMessage handleValidationException(ConstraintViolationException e) {
        return service.handleValidationException(e);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationExceptionMessage handleBadInput(MethodArgumentNotValidException e) {
        return service.handleBadInput(e);
    }

}
