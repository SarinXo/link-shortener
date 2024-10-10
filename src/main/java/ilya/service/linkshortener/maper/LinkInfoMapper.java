package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.model.LinkInfo;

import java.util.UUID;

public class LinkInfoMapper {

    public static LinkInfo requestToModel(CreateLinkInfoRequest req, String shortLink) {
        return new LinkInfo(
                UUID.randomUUID(),
                shortLink,
                0L,
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }

    public static CreateLinkInfoResponse modelToResponse(LinkInfo linkInfo) {
        return new CreateLinkInfoResponse(
                linkInfo.getId(),
                linkInfo.getShortLink(),
                linkInfo.getOpeningCount(),
                linkInfo.getLink(),
                linkInfo.getEndTime(),
                linkInfo.getDescription(),
                linkInfo.getIsActive()
        );
    }

}
