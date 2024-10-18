package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.model.LinkInfo;

public class LinkInfoMapper {

    public static LinkInfo requestToModel(LinkInfoRequest req, String shortLink) {
        return new LinkInfo(
                null,
                shortLink,
                0L,
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }

    public static LinkInfoResponse modelToResponse(LinkInfo linkInfo) {
        return new LinkInfoResponse(
                linkInfo.getId(),
                linkInfo.getShortLink(),
                linkInfo.getOpeningCount(),
                linkInfo.getLink(),
                linkInfo.getEndTime(),
                linkInfo.getDescription(),
                linkInfo.getIsActive()
        );
    }

    public static LinkInfo updateRequestToModel(UpdateLinkInfoRequest req) {
        return LinkInfo.builder()
                .id(req.id())
                .link(req.link())
                .endTime(req.endTime())
                .description(req.description())
                .isActive(req.isActive())
                .build();
    }

}
