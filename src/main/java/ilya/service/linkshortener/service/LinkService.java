package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.model.LinkInfo;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface LinkService {
    @NonNull
    LinkInfo create(LinkInfoCreateDto dto);

    @NonNull
    LinkInfo update(LinkInfoUpdateDto dto);

    void delete(UUID id);

    @NonNull
    LinkInfo getById(UUID id);

    @NonNull
    LinkInfo getByShortLink(String shortLink);

    @NonNull
    String getLinkByShortLink(String shortLink);

    @NonNull
    List<LinkInfo> getAllLinks();
}
