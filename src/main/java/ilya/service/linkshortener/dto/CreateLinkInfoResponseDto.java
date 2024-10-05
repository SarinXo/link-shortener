package ilya.service.linkshortener.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateLinkInfoResponseDto(
        @NotEmpty String shortLink
) { }
