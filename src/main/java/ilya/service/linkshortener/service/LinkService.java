package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;

public interface LinkService {
    LinkInfoResponse createLinkInfo(LinkInfoRequest requestDto);

    LinkInfoResponse getByShortLink(String shortLink);

    GetAllLinkInfoResponse findByFilter();
}
