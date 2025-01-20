package ilya.service.linkshortener.dto.service;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LinkInfoFilterDto(
        String linkPart,
        String descriptionPart,
        LocalDateTime fromEndTime,
        LocalDateTime toEndTime,
        Boolean isActive
) {
}