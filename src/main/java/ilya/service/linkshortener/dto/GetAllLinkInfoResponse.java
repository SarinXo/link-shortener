package ilya.service.linkshortener.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record GetAllLinkInfoResponse(
        @NotNull List<LinkInfoResponse> links
) {
}
