package ilya.service.linkshortener.dto.controller.request;

import ilya.service.linkshortener.validation.UuidString;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LinkInfoUpdateRequest(
        @NotNull
        @UuidString
        String id,
        String link,
        LocalDateTime endTime,
        String description,
        Boolean isActive
) {
}
