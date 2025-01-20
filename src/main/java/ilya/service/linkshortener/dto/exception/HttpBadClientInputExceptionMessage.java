package ilya.service.linkshortener.dto.exception;

public record HttpBadClientInputExceptionMessage(
    String cause
) implements ExceptionMessage {
}
