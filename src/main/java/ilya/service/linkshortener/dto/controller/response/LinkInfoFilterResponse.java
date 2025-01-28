package ilya.service.linkshortener.dto.controller.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record LinkInfoFilterResponse(
        @NotNull List<LinkInfoResponse> links
) {
}
