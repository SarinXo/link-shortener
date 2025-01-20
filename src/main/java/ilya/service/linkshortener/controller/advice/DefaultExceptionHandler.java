package ilya.service.linkshortener.controller.advice;

import ilya.service.linkshortener.dto.exception.ExceptionMessage;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.service.exception.DefaultExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order
@RestControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    private final DefaultExceptionService service;

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse<ExceptionMessage> unexpectedException(Exception e) {
        ExceptionMessage body = service.handleDefault(e);

        return CommonResponse.of(body);
    }
}
