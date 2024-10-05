package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.CreateLinkInfoRequestDto;
import ilya.service.linkshortener.dto.CreateLinkInfoResponseDto;

public interface LinkService {
    CreateLinkInfoResponseDto shortenLink(CreateLinkInfoRequestDto requestDto);

}
