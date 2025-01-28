package ilya.service.linkshortener.config;

import ilya.service.linkshortener.service.exception.DefaultExceptionService;
import ilya.service.linkshortener.service.exception.LinkExceptionService;
import ilya.service.linkshortener.service.exception.impl.DefaultExceptionServiceImpl;
import ilya.service.linkshortener.service.exception.impl.LinkExceptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@RequiredArgsConstructor
public class AppTestConfiguration {

    @Bean
    public DefaultExceptionService defaultExceptionService() {
        return new DefaultExceptionServiceImpl();
    }

    @Bean
    public LinkExceptionService linkExceptionService() {
        return new LinkExceptionServiceImpl(defaultExceptionService());
    }

    @Bean("NOT_FOUND_PAGE")
    public String notFoundPage() {
        return "NOT_FOUND_PAGE";
    }

}
