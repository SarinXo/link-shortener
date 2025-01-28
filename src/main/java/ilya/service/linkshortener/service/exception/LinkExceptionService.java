package ilya.service.linkshortener.service.exception;

import ilya.service.linkshortener.dto.exception.ExceptionMessage;
import ilya.service.linkshortener.dto.exception.ValidationExceptionMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface LinkExceptionService {

    ValidationExceptionMessage handleValidationException(ConstraintViolationException e);

    ValidationExceptionMessage handleBadInput(MethodArgumentNotValidException e);

    ExceptionMessage handleBadClientInput(HttpMessageNotReadableException e);

}
