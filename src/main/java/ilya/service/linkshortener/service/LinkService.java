package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoFilterDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.model.LinkInfoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface LinkService {

    LinkInfoEntity create(LinkInfoCreateDto dto);

    LinkInfoEntity update(LinkInfoUpdateDto dto);

    void delete(UUID id);

    LinkInfoEntity getById(UUID id);

    LinkInfoEntity getByShortLink(String shortLink);

    String getLinkByShortLink(String shortLink);

    Slice<LinkInfoEntity> getLinksByFilter(LinkInfoFilterDto filterDto, Pageable pageable);
}
