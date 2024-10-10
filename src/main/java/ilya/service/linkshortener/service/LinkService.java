package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;

public interface LinkService {
    CreateLinkInfoResponse shortenLink(CreateLinkInfoRequest requestDto);

}
