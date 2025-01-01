package ilya.service.linkshortener.config.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "link-shortener")
public record LinkInfoProperties(
        @NotNull(message = "short link length can't be null")
        @Min(message = "short link length cannot be less than 8", value = 8)
        Integer shortLinkLength
) {
}

