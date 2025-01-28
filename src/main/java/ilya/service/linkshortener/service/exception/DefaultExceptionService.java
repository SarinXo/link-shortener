package ilya.service.linkshortener.service.exception;

import ilya.service.linkshortener.dto.exception.DefaultEndpointExceptionMessage;

public interface DefaultExceptionService {
    DefaultEndpointExceptionMessage handleDefault(Exception e);
}
