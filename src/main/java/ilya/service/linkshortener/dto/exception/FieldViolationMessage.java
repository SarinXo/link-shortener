package ilya.service.linkshortener.dto.exception;

public record FieldViolationMessage(
        String field,
        String message,
        String invalidValue
) implements ExceptionMessage {
}