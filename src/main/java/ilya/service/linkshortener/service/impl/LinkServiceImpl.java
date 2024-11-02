package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.beanpostprocessor.LogTime;
import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.exception.NotFoundException;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    public LinkInfoResponse createLinkInfo(CommonRequest<LinkInfoRequest> requestDto) {
        String shortLink = RandomStringUtils.randomAlphanumeric(linkInfoProperties.shortLinkLength());
        LinkInfo linkInfo = LinkInfoMapper.requestToModel(requestDto.body(), shortLink);
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

    @Override
    public void delete(UUID id) {
        linkInfoRepositoryImpl.delete(id);
    }

    @Override
    public LinkInfoResponse update(CommonRequest<UpdateLinkInfoRequest> request) {
        val body = request.body();
        return linkInfoRepositoryImpl
                .findById(body.id())
                .map(it -> updateFromRequest(it, body))
                .map(LinkInfoMapper::modelToResponse)
                .orElseThrow(() -> new NotFoundException("LinkInfo with id = " + body.id() + " not found"));
    }

    /**
     * Мутирует данные в map link info, а в репозитории указаны ссылка на этот объект,
     * поэтому в репозитории тоже будет меняться этот объект
     *
     * @param linkInfo - сущность из репозитория
     * @param request  - запрос с данными на изменение
     * @return измененный <b>linkInfo</b> объект
     */
    private LinkInfo updateFromRequest(LinkInfo linkInfo, UpdateLinkInfoRequest request) {
        if (StringUtils.hasText(request.link())) {
            linkInfo.setLink(request.link());
        }
        if (Objects.nonNull(request.endTime())) {
            linkInfo.setEndTime(request.endTime());
        }
        if (StringUtils.hasText(request.description())) {
            linkInfo.setDescription(request.description());
        }
        if (Objects.nonNull(request.isActive())) {
            linkInfo.setIsActive(request.isActive());
        }
        return linkInfo;
    }

    @Override
    public String getTargetLink(String shortLink) {
        LinkInfo linkInfo = linkInfoRepositoryImpl
                .findByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundException(shortLink + " not found"));

        return linkInfo.getLink();
    }

}
