package ilya.service.linkshortener.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateLinkInfoResponse(
        @NotEmpty String shortLink
) {
}
