package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.model.LinkInfo;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository {
    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfo);

    @NonNull
    List<LinkInfo> findAll();

    Optional<LinkInfo> findById(UUID id);

    void delete(UUID id);

}
