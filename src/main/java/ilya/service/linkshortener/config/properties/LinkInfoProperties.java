package ilya.service.linkshortener.config.properties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.links")
public class LinkInfoProperties {

    @NotNull(message = "short link length can't be null")
    @Positive(message = "short link length can't be 0 or less")
    private final Integer shortLinkLength;

}

