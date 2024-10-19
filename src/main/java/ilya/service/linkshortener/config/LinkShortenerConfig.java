package ilya.service.linkshortener.config;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import ilya.service.linkshortener.service.impl.LinkServiceImpl;
import ilya.service.linkshortener.service.impl.LogExecutionTimeLinkInfoServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LinkShortenerConfig {

    private final LinkInfoProperties properties;
    private final LinkInfoRepository repository;

    @Bean
    public LinkService linkServiceImpl() {
        var linkService = new LinkServiceImpl(properties, repository);
        var proxy = new LogExecutionTimeLinkInfoServiceProxy(linkService);

        return proxy;
    }

}
