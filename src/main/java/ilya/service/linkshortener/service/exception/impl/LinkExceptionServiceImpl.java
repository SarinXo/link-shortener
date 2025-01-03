package ilya.service.linkshortener.service.exception.impl;

import ilya.service.linkshortener.dto.exception.FieldViolationMessage;
import ilya.service.linkshortener.dto.exception.ValidationExceptionMessage;
import ilya.service.linkshortener.service.exception.LinkExceptionService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkExceptionServiceImpl implements LinkExceptionService {

    @Override
    public ValidationExceptionMessage handleValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(this::extractFieldViolationMessage)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ValidationExceptionMessage::new
                ));
    }

    @Override
    public ValidationExceptionMessage handleBadInput(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(this::extractBadInput)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ValidationExceptionMessage::new
                ));
    }

    private FieldViolationMessage extractFieldViolationMessage(ConstraintViolation<?> violation) {
        String field = violation.getPropertyPath().toString();
        String message = violation.getMessage();
        String invalidValue = violation.getInvalidValue().toString();

        return new FieldViolationMessage(field, message, invalidValue);
    }

    private FieldViolationMessage extractBadInput(FieldError error) {
        String field = error.getField();
        String message = error.getDefaultMessage();
        String invalidArguments = error.getArguments() != null
                ? Arrays.toString(error.getArguments())
                : "null";

        return new FieldViolationMessage(field, message, invalidArguments);
    }
}
