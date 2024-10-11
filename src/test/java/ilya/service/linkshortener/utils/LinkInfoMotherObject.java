package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.model.LinkInfo;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class LinkInfoMotherObject {

    private UUID id;
    private String shortLink;
    private Long openingCount;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean isActive;

    public LinkInfoMotherObject setId(UUID id) {
        this.id = id;
        return this;
    }

    public LinkInfoMotherObject setShortLink(String shortLink) {
        this.shortLink = shortLink;
        return this;
    }

    public LinkInfoMotherObject setOpeningCount(Long openingCount) {
        this.openingCount = openingCount;
        return this;
    }

    public LinkInfoMotherObject setLink(String link) {
        this.link = link;
        return this;
    }

    public LinkInfoMotherObject setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public LinkInfoMotherObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public LinkInfoMotherObject setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public LinkInfoMotherObject random() {
        this.id = UUID.randomUUID();
        this.shortLink = RandomStringUtils.randomAlphanumeric(10);
        this.openingCount = 0L;
        this.link = RandomStringUtils.randomAlphanumeric(15);
        this.endTime = LocalDateTime.now();
        this.description = RandomStringUtils.randomAlphanumeric(15);
        this.isActive = true;
        return this;
    }

    public LinkInfo build() {
        return new LinkInfo(
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
