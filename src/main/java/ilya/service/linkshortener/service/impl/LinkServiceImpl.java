package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoFilterDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.exception.NotFoundException;
import ilya.service.linkshortener.exception.NotFoundLinkException;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfoEntity;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ilya.service.linkshortener.model.LinkInfoEntity_.DESCRIPTION;
import static ilya.service.linkshortener.model.LinkInfoEntity_.END_TIME;
import static ilya.service.linkshortener.model.LinkInfoEntity_.IS_ACTIVE;
import static ilya.service.linkshortener.model.LinkInfoEntity_.LINK;
import static ilya.service.linkshortener.repository.specification.LinkInfoSpecificationFilter.afterOrEqualTime;
import static ilya.service.linkshortener.repository.specification.LinkInfoSpecificationFilter.beforeOrEqualTime;
import static ilya.service.linkshortener.repository.specification.LinkInfoSpecificationFilter.isEqual;
import static ilya.service.linkshortener.repository.specification.LinkInfoSpecificationFilter.isFieldInclude;

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
    public LinkInfoEntity create(LinkInfoCreateDto dto) {
        LinkInfoEntity linkInfo = mapper.createDtoToModel(dto);

        String shortLink = RandomStringUtils.randomAlphanumeric(linkInfoProperties.shortLinkLength());
        linkInfo.setShortLink(shortLink);

        return linkInfoRepository.save(linkInfo);
    }

    @Override
    public LinkInfoEntity update(LinkInfoUpdateDto dto) {
        LinkInfoEntity linkInfo = getById(dto.id());
        updateFromDto(linkInfo, dto);

        return linkInfoRepository.save(linkInfo);
    }

    @Override
    public void delete(UUID id) {
        linkInfoRepository.deleteById(id);
    }

    @Override
    public LinkInfoEntity getById(UUID id) {
        return linkInfoRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("LinkInfo with id = " + id + "not found")
                );
    }

    @Override
    public LinkInfoEntity getByShortLink(String shortLink) {
        return linkInfoRepository
                .findActiveLinkByShortLink(shortLink)
                .orElseThrow(
                        () -> new NotFoundException("LinkInfo with short link = '" + shortLink + "' doesn't exist")
                );
    }

    @Override
    public String getLinkByShortLink(String shortLink) {
        LinkInfoEntity linkInfo = linkInfoRepository
                .findActiveLinkByShortLink(shortLink)
                .orElseThrow(
                        () -> new NotFoundLinkException("Link with short link = '" + shortLink + "' doesn't exist")
                );
        linkInfoRepository.incrementOpeningCountByShortLink(linkInfo.getShortLink());

        return linkInfo.getLink();
    }

    //todo приделать Slice к запросу
    @Override
    public List<LinkInfoEntity> getLinksByFilter(LinkInfoFilterDto filterDto) {
        Specification<LinkInfoEntity> spec = Specification
                .where(isFieldInclude(LINK, filterDto.linkPart()))
                .and(isFieldInclude(DESCRIPTION, filterDto.descriptionPart()))
                .and(afterOrEqualTime(END_TIME, filterDto.fromEndTime()))
                .and(beforeOrEqualTime(END_TIME, filterDto.toEndTime()))
                .and(isEqual(IS_ACTIVE, filterDto.isActive()));

        return linkInfoRepository.findAll(spec);
    }

    private LinkInfoEntity updateFromDto(LinkInfoEntity linkInfo, LinkInfoUpdateDto dto) {
        if (StringUtils.hasText(dto.link())) {
            linkInfo.setLink(dto.link());
        }
        linkInfo.setEndTime(dto.endTime());
        if (StringUtils.hasText(dto.description())) {
            linkInfo.setDescription(dto.description());
        }
        if (Objects.nonNull(dto.isActive())) {
            linkInfo.setIsActive(dto.isActive());
        }
        return linkInfo;
    }

}
