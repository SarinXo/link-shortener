package ilya.service.linkshortener.dto;

import ilya.service.linkshortener.model.LinkInfo;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GetAllLinkInfoResponse (
    @NotNull List<LinkInfo> links
) { }
