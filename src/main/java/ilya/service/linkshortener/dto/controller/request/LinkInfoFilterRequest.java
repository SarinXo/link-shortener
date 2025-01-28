package ilya.service.linkshortener.dto.controller.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LinkInfoFilterRequest(
        String linkPart,
        String descriptionPart,
        LocalDateTime fromEndTime,
        LocalDateTime toEndTime,
        Boolean isActive
) {
}