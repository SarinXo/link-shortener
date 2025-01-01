package ilya.service.linkshortener.config;

import ilya.service.linkshortener.exception.service.DefaultExceptionService;
import ilya.service.linkshortener.exception.service.LinkExceptionService;
import ilya.service.linkshortener.exception.service.impl.DefaultExceptionServiceImpl;
import ilya.service.linkshortener.exception.service.impl.LinkExceptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

@TestConfiguration
@RequiredArgsConstructor
public class AppTestConfiguration {
    private final ResourceLoader resourceLoader;


    @Bean
    public DefaultExceptionService defaultExceptionService() {
        return new DefaultExceptionServiceImpl();
    }
    @Bean
    public LinkExceptionService linkExceptionService() {
        return new LinkExceptionServiceImpl(resourceLoader);
    }

}
