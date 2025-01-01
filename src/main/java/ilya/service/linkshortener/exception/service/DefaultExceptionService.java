package ilya.service.linkshortener.exception.service;

import ilya.service.linkshortener.exception.dto.DefaultEndpointExceptionMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface DefaultExceptionService {
    ResponseEntity<DefaultEndpointExceptionMessage> handleDefault(Exception e, HttpServletRequest request);
}
