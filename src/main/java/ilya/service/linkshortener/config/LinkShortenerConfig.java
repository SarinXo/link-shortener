package ilya.service.linkshortener.config;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import ilya.service.linkshortener.service.impl.LinkServiceImpl;
import ilya.service.linkshortener.service.impl.LogExecutionTimeLinkInfoServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LinkShortenerConfig {

    private final ApplicationContext context;

    @Bean
    public LinkService linkServiceImpl() {
        var props = context.getBean(LinkInfoProperties.class);
        var repository = context.getBean(LinkInfoRepository.class);

        var linkService = new LinkServiceImpl(props, repository);
        var proxy = new LogExecutionTimeLinkInfoServiceProxy(linkService);

        return proxy;
    }

}
