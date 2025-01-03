package ilya.service.linkshortener.controller.advice;

import ilya.service.linkshortener.dto.exception.DefaultEndpointExceptionMessage;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.service.exception.DefaultExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order
@RestControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    private final DefaultExceptionService service;

    @ExceptionHandler(Exception.class)
    public CommonResponse<DefaultEndpointExceptionMessage> unexpectedException(Exception e, HttpServletRequest request) {
        DefaultEndpointExceptionMessage body = service.handleDefault(e, request);

        return CommonResponse.of(body);
    }
}
