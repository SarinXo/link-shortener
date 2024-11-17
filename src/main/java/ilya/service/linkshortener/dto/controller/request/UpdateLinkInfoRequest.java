package ilya.service.linkshortener.dto.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UpdateLinkInfoRequest(
        @NotNull UUID id,
        String link,
        LocalDateTime endTime,
        String description,
        Boolean isActive
) {
}
