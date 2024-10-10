package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.GetLinkInfoResponse;

public interface LinkService {
    CreateLinkInfoResponse createLinkInfo(CreateLinkInfoRequest requestDto);

    GetLinkInfoResponse getByShortLink(String shortLink);

    GetAllLinkInfoResponse findByFilter();
}
