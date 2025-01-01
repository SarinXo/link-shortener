package ilya.service.linkshortener.dto.controller.request;

import ilya.service.linkshortener.validation.UuidString;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UpdateLinkInfoRequest(
        @NotNull
        @UuidString
        String id,
        String link,
        LocalDateTime endTime,
        String description,
        Boolean isActive
) {
}
