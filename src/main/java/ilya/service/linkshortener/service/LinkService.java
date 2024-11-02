package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;

import java.util.UUID;

public interface LinkService {
    LinkInfoResponse createLinkInfo(CommonRequest<LinkInfoRequest> requestDto);

    LinkInfoResponse getByShortLink(String shortLink);

    GetAllLinkInfoResponse findByFilter();

    void delete(UUID id);

    LinkInfoResponse update(CommonRequest<UpdateLinkInfoRequest> request);

    String getTargetLink(String shortLink);
}
