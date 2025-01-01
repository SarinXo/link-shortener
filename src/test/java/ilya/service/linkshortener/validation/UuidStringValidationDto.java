package ilya.service.linkshortener.validation;

public record UuidStringValidationDto(
        @UuidString
        String uuid
) {
}
