package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.model.LinkInfoEntity;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface LinkService {
    @NonNull
    LinkInfoEntity create(LinkInfoCreateDto dto);

    @NonNull
    LinkInfoEntity update(LinkInfoUpdateDto dto);

    void delete(UUID id);

    @NonNull
    LinkInfoEntity getById(UUID id);

    @NonNull
    LinkInfoEntity getByShortLink(String shortLink);

    @NonNull
    String getLinkByShortLink(String shortLink);

    @NonNull
    List<LinkInfoEntity> getAllLinks();
}
