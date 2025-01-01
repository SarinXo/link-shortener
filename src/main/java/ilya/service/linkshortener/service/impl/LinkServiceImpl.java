package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.exception.model.NotFoundException;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class LinkServiceImpl implements LinkService {

    private LinkInfoProperties linkInfoProperties;
    private LinkInfoMapper mapper;
    private LinkInfoRepository linkInfoRepository;

    @Autowired
    public void setMapper(LinkInfoMapper linkInfoMapper) {
        this.mapper = linkInfoMapper;
    }

    @Autowired
    public void setLinkInfoProperties(LinkInfoProperties linkInfoProperties) {
        this.linkInfoProperties = linkInfoProperties;
    }

    @Autowired
    public void setLinkInfoRepositoryImpl(LinkInfoRepository linkInfoRepository) {
        this.linkInfoRepository = linkInfoRepository;
    }

    @Override
    public LinkInfo create(LinkInfoCreateDto dto) {
        LinkInfo linkInfo = mapper.createDtoToModel(dto);

        String shortLink = RandomStringUtils.randomAlphanumeric(linkInfoProperties.shortLinkLength());
        linkInfo.setShortLink(shortLink);

        return linkInfoRepository.save(linkInfo);
    }

    @Override
    public LinkInfo update(LinkInfoUpdateDto dto) {
        LinkInfo linkInfo = getById(dto.id());
        updateFromDto(linkInfo, dto);

        return linkInfoRepository.save(linkInfo);
    }

    private LinkInfo updateFromDto(LinkInfo linkInfo, LinkInfoUpdateDto dto) {
        if (StringUtils.hasText(dto.link())) {
            linkInfo.setLink(dto.link());
        }
        if (Objects.nonNull(dto.endTime())) {
            linkInfo.setEndTime(dto.endTime());
        }
        if (StringUtils.hasText(dto.description())) {
            linkInfo.setDescription(dto.description());
        }
        if (Objects.nonNull(dto.isActive())) {
            linkInfo.setIsActive(dto.isActive());
        }
        return linkInfo;
    }

    @Override
    public void delete(UUID id) {
        linkInfoRepository.delete(id);
    }

    @Override
    public LinkInfo getById(UUID id) {
        return linkInfoRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("LinkInfo with id = " + id + "not found")
                );
    }

    @Override
    public LinkInfo getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .filter(this::isAvailableLink)
                .orElseThrow(
                        () -> new NotFoundException("LinkInfo with short link = '" + shortLink + "' doesn't exist")
                );
    }

    private boolean isAvailableLink(LinkInfo linkInfo) {
        boolean isActive = Boolean.TRUE.equals(linkInfo.getIsActive());
        boolean isNotExpired = LocalDateTime.now().isBefore(linkInfo.getEndTime());

        return isActive && isNotExpired;
    }

    @Override
    public String getLinkByShortLink(String shortLink) {
        return getByShortLink(shortLink).getLink();
    }

    @Override
    public List<LinkInfo> getAllLinks() {
        return linkInfoRepository.findAll();
    }
}
