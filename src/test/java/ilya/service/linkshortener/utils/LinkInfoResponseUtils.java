package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.LinkInfoResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class LinkInfoResponseUtils {

    public static LinkInfoResponse.LinkInfoResponseBuilder random() {
        return LinkInfoResponse.builder()
                .id(UUID.randomUUID())
                .shortLink(RandomStringUtils.randomAlphanumeric(10))
                .openingCount(0L)
                .link(RandomStringUtils.randomAlphanumeric(10))
                .endTime(LocalDateTime.now().plusDays(1))
                .description(RandomStringUtils.randomAlphanumeric(10))
                .isActive(true);
    }
}
