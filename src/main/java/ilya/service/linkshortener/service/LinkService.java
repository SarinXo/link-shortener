package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;

public interface LinkService {
    LinkInfoResponse createLinkInfo(CreateLinkInfoRequest requestDto);

    LinkInfoResponse getByShortLink(String shortLink);

    GetAllLinkInfoResponse findByFilter();
}
