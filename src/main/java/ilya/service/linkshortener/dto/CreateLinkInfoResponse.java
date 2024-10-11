package ilya.service.linkshortener.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateLinkInfoResponse(
        @NotNull UUID id,
        @NotEmpty String shortLink,
        @NotNull Long openingCount,
        @NotEmpty String link,
        @NotNull LocalDateTime endTime,
        @Nullable String description,
        @NotNull Boolean isActive
) {
}
