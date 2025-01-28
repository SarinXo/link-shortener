package ilya.service.linkshortener.dto.exception;


import java.time.LocalDateTime;

public record DefaultEndpointExceptionMessage(
        LocalDateTime timestamp,
        String message,
        String type
) implements ExceptionMessage {
}