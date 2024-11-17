package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class LinkInfoCreateDtoUtils {

    public static LinkInfoCreateDto.LinkInfoCreateDtoBuilder random() {
        return LinkInfoCreateDto.builder()
                .link(RandomStringUtils.randomAlphanumeric(10))
                .endTime(LocalDateTime.now().plusDays(1))
                .description(RandomStringUtils.randomAlphanumeric(10))
                .isActive(true);
    }
}
