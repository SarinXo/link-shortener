package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.controller.request.UpdateLinkInfoRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateLinkInfoRequestUtils {

    public static UpdateLinkInfoRequest.UpdateLinkInfoRequestBuilder random() {
        return UpdateLinkInfoRequest.builder()
                .id(UUID.randomUUID().toString())
                .link(RandomStringUtils.randomAlphanumeric(10))
                .endTime(LocalDateTime.now().plusDays(1))
                .description(RandomStringUtils.randomAlphanumeric(10))
                .isActive(true);
    }
}
