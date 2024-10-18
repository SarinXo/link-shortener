package ilya.service.linkshortener.repository.impl;

import ch.qos.logback.core.util.StringUtil;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final static ConcurrentMap<String, LinkInfo> shortLinkStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        LinkInfo linkInfo = shortLinkStorage.get(shortLink);

        return Optional.ofNullable(linkInfo);
    }

    @Override
    public LinkInfo save(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());

        String key = linkInfo.getShortLink();
        shortLinkStorage.put(key, linkInfo);

        return linkInfo;
    }

    @Override
    public List<LinkInfo> findAll() {
        return shortLinkStorage.values()
                .stream()
                .toList();
    }

    private Optional<LinkInfo> findById(UUID id) {
        for(LinkInfo linkInfo : shortLinkStorage.values()) {
            if(linkInfo.getId().equals(id)) {
                return Optional.of(linkInfo);
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {
        Optional<LinkInfo> linkInfo = findById(id);
        linkInfo.ifPresent(
                it -> shortLinkStorage.remove(it.getShortLink())
        );
    }

    @Override
    public Optional<LinkInfo> update(LinkInfo link) {
        Optional<LinkInfo> linkInfo = findById(link.getId());
        linkInfo.ifPresent(
                it -> {
                    if(!StringUtil.isNullOrEmpty(link.getLink())) {
                        it.setLink(link.getLink());
                    }
                    if(Objects.nonNull(link.getLink())) {
                        it.setEndTime(link.getEndTime());
                    }
                    if(!StringUtil.isNullOrEmpty(link.getDescription())) {
                        it.setDescription(link.getDescription());
                    }
                    if(Objects.nonNull(link.getIsActive())) {
                        it.setIsActive(link.getIsActive());
                    }
                }
        );
        return linkInfo;
    }
}