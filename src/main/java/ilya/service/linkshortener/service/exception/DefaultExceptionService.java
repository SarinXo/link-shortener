package ilya.service.linkshortener.service.exception;

import ilya.service.linkshortener.dto.exception.DefaultEndpointExceptionMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface DefaultExceptionService {
    DefaultEndpointExceptionMessage handleDefault(Exception e, HttpServletRequest request);
}
