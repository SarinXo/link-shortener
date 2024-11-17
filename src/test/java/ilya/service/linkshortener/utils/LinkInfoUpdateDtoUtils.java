package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class LinkInfoUpdateDtoUtils {

    public static LinkInfoUpdateDto.LinkInfoUpdateDtoBuilder random() {
        return LinkInfoUpdateDto.builder()
                .id(UUID.randomUUID())
                .link(RandomStringUtils.randomAlphanumeric(10))
                .endTime(LocalDateTime.now().plusDays(1))
                .description(RandomStringUtils.randomAlphanumeric(10))
                .isActive(true);
    }
}
