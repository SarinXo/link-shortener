package ilya.service.linkshortener.exception.dto;

public record FieldViolationMessage(
    String field,
    String message,
    String invalidValue
) { }