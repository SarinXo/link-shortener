package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class LinkServiceImpl implements LinkService {
    private final static Integer BASE_SHORT_LINK_LENGTH = 10;

    private final static ConcurrentMap<String, LinkInfo> shortLinkStorage = new ConcurrentHashMap<>();

    @Override
    public CreateLinkInfoResponse shortenLink(CreateLinkInfoRequest requestDto) {
        String shortLink = RandomStringUtils.randomAlphanumeric(BASE_SHORT_LINK_LENGTH);
        LinkInfo linkInfo = LinkInfoMapper.requestToModel(requestDto, shortLink);
        shortLinkStorage.put(shortLink, linkInfo);
        log.debug("new '{}' short link added in map", shortLink);

        return LinkInfoMapper.modelToResponse(linkInfo);
    }

}
