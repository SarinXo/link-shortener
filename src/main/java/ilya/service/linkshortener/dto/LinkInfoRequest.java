package ilya.service.linkshortener.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LinkInfoRequest(
        @NotNull String link,
        @NotNull LocalDateTime endTime,
        @Nullable String description,
        @NotNull Boolean isActive
) {
}
