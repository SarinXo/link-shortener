package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.beanpostprocessor.LogTime;
import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.exception.NotFoundException;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LinkServiceImpl implements LinkService {
    //todo исправить после деактивации LogTimeBeanPostProcessor

    private LinkInfoProperties linkInfoProperties;
    private LinkInfoRepository linkInfoRepositoryImpl;

    @Autowired
    public void setLinkInfoProperties(LinkInfoProperties linkInfoProperties) {
        this.linkInfoProperties = linkInfoProperties;
    }

    @Autowired
    public void setLinkInfoRepositoryImpl(LinkInfoRepository linkInfoRepositoryImpl) {
        this.linkInfoRepositoryImpl = linkInfoRepositoryImpl;
    }

    @LogTime(methodName = "Create Link")
    @Override
    public LinkInfoResponse createLinkInfo(LinkInfoRequest requestDto) {
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
                .orElseThrow(() -> new NotFoundException(shortLink + " not found"));
    }

    @Override
    public GetAllLinkInfoResponse findByFilter() {
        List<LinkInfo> links = linkInfoRepositoryImpl.findAll();
        return new GetAllLinkInfoResponse(links);
    }
}
