package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.exception.NotFoundException;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkInfoProperties linkInfoProperties;
    private final LinkInfoRepository linkInfoRepositoryImpl;

    @Override
    public LinkInfoResponse createLinkInfo(CreateLinkInfoRequest requestDto) {
        String shortLink = RandomStringUtils.randomAlphanumeric(linkInfoProperties.shortLinkLength());
        LinkInfo linkInfo = LinkInfoMapper.requestToModel(requestDto, shortLink);
        linkInfoRepositoryImpl.save(linkInfo);
        log.debug("new '{}' short link added in map", shortLink);

        return LinkInfoMapper.modelToResponse(linkInfo);
    }

    @Override
    public LinkInfoResponse getByShortLink(String shortLink) {
        return linkInfoRepositoryImpl.findByShortLink(shortLink)
                .map(LinkInfoMapper::modelToResponse)
                .orElseThrow(() -> {
                    log.error("short link '{}' was not found in repository", shortLink);
                    throw new NotFoundException(shortLink + " not found");
                });
    }

    @Override
    public GetAllLinkInfoResponse findByFilter() {
        List<LinkInfo> links = linkInfoRepositoryImpl.findAll();
        return new GetAllLinkInfoResponse(links);
    }
}
