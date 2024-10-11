package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.GetLinkInfoResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class GetLinkInfoResponseMotherObject {
    private UUID id;
    private String shortLink;
    private Long openingCount;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean isActive;

    public GetLinkInfoResponseMotherObject setId(UUID id) {
        this.id = id;
        return this;
    }

    public GetLinkInfoResponseMotherObject setShortLink(String shortLink) {
        this.shortLink = shortLink;
        return this;
    }

    public GetLinkInfoResponseMotherObject setOpeningCount(Long openingCount) {
        this.openingCount = openingCount;
        return this;
    }

    public GetLinkInfoResponseMotherObject setLink(String link) {
        this.link = link;
        return this;
    }

    public GetLinkInfoResponseMotherObject setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public GetLinkInfoResponseMotherObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public GetLinkInfoResponseMotherObject setIsActive(Boolean active) {
        isActive = active;
        return this;
    }

    public GetLinkInfoResponseMotherObject random() {
        this.id = UUID.randomUUID();
        this.shortLink = RandomStringUtils.randomAlphanumeric(10);
        this.openingCount = 0L;
        this.link = RandomStringUtils.randomAlphanumeric(15);
        this.endTime = LocalDateTime.now();
        this.description = RandomStringUtils.randomAlphanumeric(15);
        this.isActive = true;
        return this;
    }

    public GetLinkInfoResponse build() {
        return new GetLinkInfoResponse(
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
