package ilya.service.linkshortener.service.exception.impl;

import ilya.service.linkshortener.dto.exception.DefaultEndpointExceptionMessage;
import ilya.service.linkshortener.service.exception.DefaultExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultExceptionServiceImpl implements DefaultExceptionService {

    @Override
    public DefaultEndpointExceptionMessage handleDefault(Exception e, HttpServletRequest request) {
        String message = e.getMessage();
        String type = e.getClass().toString();
        LocalDateTime timestamp = LocalDateTime.now();

        return new DefaultEndpointExceptionMessage(timestamp, type, message);
    }
}
