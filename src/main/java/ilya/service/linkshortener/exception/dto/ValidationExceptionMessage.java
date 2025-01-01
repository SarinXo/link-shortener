package ilya.service.linkshortener.exception.dto;

import java.util.List;

public record ValidationExceptionMessage(
        List<FieldViolationMessage> violations
) { }
