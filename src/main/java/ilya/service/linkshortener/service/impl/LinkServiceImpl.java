package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.CreateLinkInfoRequestDto;
import ilya.service.linkshortener.dto.CreateLinkInfoResponseDto;
import ilya.service.linkshortener.dto.LinkInfoDto;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LinkServiceImpl implements LinkService {
    private final static Integer BASE_SHORT_LINK_LENGTH = 10;

    private final static Map<String, LinkInfoDto> shortLinkStorage = new ConcurrentHashMap<>();

    @Override
    public CreateLinkInfoResponseDto shortenLink(CreateLinkInfoRequestDto requestDto) {
        String shortLink = RandomStringUtils.randomAlphanumeric(BASE_SHORT_LINK_LENGTH);
        LinkInfoDto linkInfo = LinkInfoMapper.requestToDto(requestDto);
        shortLinkStorage.put(shortLink, linkInfo);
        log.debug("new '{}' short link added in map", shortLink);

        return new CreateLinkInfoResponseDto(shortLink);
    }

}
