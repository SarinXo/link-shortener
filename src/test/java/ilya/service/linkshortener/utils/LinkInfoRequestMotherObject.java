package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class LinkInfoRequestMotherObject {
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean isActive;

    public LinkInfoRequestMotherObject setLink(String link) {
        this.link = link;
        return this;
    }

    public LinkInfoRequestMotherObject setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public LinkInfoRequestMotherObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public LinkInfoRequestMotherObject setActive(boolean active) {
        isActive = active;
        return this;
    }

    public LinkInfoRequestMotherObject random() {
        this.link = RandomStringUtils.randomAlphanumeric(15);
        this.endTime = LocalDateTime.now();
        this.description = RandomStringUtils.randomAlphanumeric(15);
        this.isActive = true;
        return this;
    }

    public CreateLinkInfoRequest build() {
        return new CreateLinkInfoRequest(
                this.link,
                this.endTime,
                this.description,
                this.isActive
        );
    }
}
