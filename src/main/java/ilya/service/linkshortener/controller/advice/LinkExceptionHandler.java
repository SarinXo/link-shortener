package ilya.service.linkshortener.controller.advice;

import ilya.service.linkshortener.dto.exception.ValidationExceptionMessage;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.service.exception.LinkExceptionService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RestControllerAdvice
@RequiredArgsConstructor
public class LinkExceptionHandler {

    private final LinkExceptionService service;
    private final String NOT_FOUND_PAGE;

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_HTML)
                .body(NOT_FOUND_PAGE);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse<ValidationExceptionMessage> handleValidationException(ConstraintViolationException e) {
        ValidationExceptionMessage body = service.handleValidationException(e);

        return CommonResponse.of(body);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<ValidationExceptionMessage> handleBadInput(MethodArgumentNotValidException e) {
        ValidationExceptionMessage body = service.handleBadInput(e);

        return CommonResponse.of(body);
    }

}
