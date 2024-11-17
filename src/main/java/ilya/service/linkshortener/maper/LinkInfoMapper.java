package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.controller.request.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoResponse;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
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

    public static LinkInfo createDtoToModel(LinkInfoCreateDto dto) {
        return new LinkInfo(
                null,
                null,
                0L,
                dto.link(),
                dto.endTime(),
                dto.description(),
                dto.isActive()
        );
    }

    public static LinkInfoCreateDto requestToCreateDto(LinkInfoRequest req) {
        return new LinkInfoCreateDto(
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }

    public static LinkInfoUpdateDto updateRequestToUpdateDto(UpdateLinkInfoRequest req) {
        return new LinkInfoUpdateDto(
                req.id(),
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }
}
