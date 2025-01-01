package ilya.service.linkshortener.exception.service.impl;

import ilya.service.linkshortener.exception.dto.DefaultEndpointExceptionMessage;
import ilya.service.linkshortener.exception.service.DefaultExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
public class DefaultExceptionServiceImpl implements DefaultExceptionService {

    @Override
    public ResponseEntity<DefaultEndpointExceptionMessage> handleDefault(Exception e, HttpServletRequest request) {
        String message = e.getMessage();
        String type = e.getClass().toString();
        String path = request.getMethod() + " " + request.getServletPath();
        LocalDateTime timestamp = LocalDateTime.now();

        DefaultEndpointExceptionMessage body = new DefaultEndpointExceptionMessage(timestamp, type, path, message);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
