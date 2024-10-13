package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.LinkInfoResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class LinkInfoResponseMotherObject {
    private UUID id;
    private String shortLink;
    private Long openingCount;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean isActive;

    public LinkInfoResponseMotherObject setId(UUID id) {
        this.id = id;
        return this;
    }

    public LinkInfoResponseMotherObject setShortLink(String shortLink) {
        this.shortLink = shortLink;
        return this;
    }

    public LinkInfoResponseMotherObject setOpeningCount(Long openingCount) {
        this.openingCount = openingCount;
        return this;
    }

    public LinkInfoResponseMotherObject setLink(String link) {
        this.link = link;
        return this;
    }

    public LinkInfoResponseMotherObject setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public LinkInfoResponseMotherObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public LinkInfoResponseMotherObject setIsActive(Boolean active) {
        isActive = active;
        return this;
    }

    public LinkInfoResponseMotherObject random() {
        this.id = UUID.randomUUID();
        this.shortLink = RandomStringUtils.randomAlphanumeric(10);
        this.openingCount = 0L;
        this.link = RandomStringUtils.randomAlphanumeric(15);
        this.endTime = LocalDateTime.now();
        this.description = RandomStringUtils.randomAlphanumeric(15);
        this.isActive = true;
        return this;
    }

    public LinkInfoResponse build() {
        return new LinkInfoResponse(
                this.id,
                this.shortLink,
                this.openingCount,
                this.link,
                this.endTime,
                this.description,
                this.isActive
        );
    }
}
