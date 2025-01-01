package ilya.service.linkshortener.exception.service;

import ilya.service.linkshortener.exception.dto.ValidationExceptionMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface LinkExceptionService {
    String getNotFoundPage();

    ValidationExceptionMessage handleValidationException(ConstraintViolationException e);

    ValidationExceptionMessage handleBadInput(MethodArgumentNotValidException e);
}
