package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class LinkInfoRequestUtils {

    public static LinkInfoRequest.LinkInfoRequestBuilder random() {
        return LinkInfoRequest.builder()
                .link(RandomStringUtils.randomAlphanumeric(10))
                .endTime(LocalDateTime.now().plusDays(1))
                .description(RandomStringUtils.randomAlphanumeric(10))
                .isActive(true);
    }
}
