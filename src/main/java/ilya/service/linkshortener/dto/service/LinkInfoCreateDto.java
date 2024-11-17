package ilya.service.linkshortener.dto.service;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LinkInfoCreateDto(
        @NotEmpty
        String link,
        @NotNull
        LocalDateTime endTime,
        @Nullable
        String description,
        @NotNull
        Boolean isActive
) {
}
