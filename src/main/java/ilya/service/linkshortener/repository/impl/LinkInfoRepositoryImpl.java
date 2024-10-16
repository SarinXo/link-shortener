package ilya.service.linkshortener.repository.impl;

import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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

}
