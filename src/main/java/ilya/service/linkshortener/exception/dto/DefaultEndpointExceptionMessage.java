package ilya.service.linkshortener.exception.dto;


import java.time.LocalDateTime;

public record DefaultEndpointExceptionMessage(
        LocalDateTime timestamp,
        String message,
        String path,
        String type
) {
}