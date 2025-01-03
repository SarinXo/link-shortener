package ilya.service.linkshortener.service.exception;

import ilya.service.linkshortener.dto.exception.DefaultEndpointExceptionMessage;
import jakarta.servlet.http.HttpServletRequest;

public interface DefaultExceptionService {
    DefaultEndpointExceptionMessage handleDefault(Exception e, HttpServletRequest request);
}
