package ilya.service.linkshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class PageConfig {

    @Bean
    public String notFoundPage() {
        try {
            ClassPathResource resource = new ClassPathResource("/templates/404.html");
            Path path = resource.getFile().toPath();
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("unable to load 404 page");
        }
    }
}
