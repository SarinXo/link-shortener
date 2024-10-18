package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.model.LinkInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository {
    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfo);

    List<LinkInfo> findAll();

    void delete(UUID id);

    Optional<LinkInfo> update(LinkInfo link);
}
