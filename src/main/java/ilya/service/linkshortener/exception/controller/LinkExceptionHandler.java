package ilya.service.linkshortener.exception.controller;

import ilya.service.linkshortener.exception.dto.FieldViolationMessage;
import ilya.service.linkshortener.exception.dto.ValidationExceptionMessage;
import ilya.service.linkshortener.exception.model.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_HTML;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class LinkExceptionHandler {
    private final String notFoundPage;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_HTML)
                .body(notFoundPage);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationExceptionMessage handleValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(constraint -> {
                            String field = constraint.getPropertyPath().toString();
                            String message = constraint.getMessage();
                            String invalidValue = constraint.getInvalidValue().toString();

                            return new FieldViolationMessage(field, message, invalidValue);
                        }
                ).collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ValidationExceptionMessage::new
                ));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationExceptionMessage handleBadInput(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                            String field = error.getField();
                            String message = error.getDefaultMessage();
                            String invalidArguments = error.getArguments() != null
                                    ? Arrays.toString(error.getArguments())
                                    : "null";

                            return new FieldViolationMessage(field, message, invalidArguments);
                            }
                )
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ValidationExceptionMessage::new
                ));
    }
}
