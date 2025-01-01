package ilya.service.linkshortener.exception.service.impl;

import ilya.service.linkshortener.exception.dto.FieldViolationMessage;
import ilya.service.linkshortener.exception.dto.ValidationExceptionMessage;
import ilya.service.linkshortener.exception.service.LinkExceptionService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkExceptionServiceImpl implements LinkExceptionService {

    private final ResourceLoader resourceLoader;
    private String NOT_FOUND_PAGE;

    @PostConstruct
    public void loadFile() {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/404.html");
            Path filePath = resource.getFile().toPath();
            NOT_FOUND_PAGE = Files.readString(filePath);
        } catch (IOException e) {
            log.error("Unable to load 404 page!", e.getCause());
        }
    }

    @Override
    public String getNotFoundPage() {
        return NOT_FOUND_PAGE;
    }

    @Override
    public ValidationExceptionMessage handleValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(this::extractFieldViolationMessage)
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

    @Override
    public ValidationExceptionMessage handleBadInput(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(this::extractBadInput)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ValidationExceptionMessage::new
                ));
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
