package ilya.service.linkshortener.dto.exception;

import java.util.List;

public record ValidationExceptionMessage(
        List<FieldViolationMessage> violations
) {
}
