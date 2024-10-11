package ilya.service.linkshortener.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkInfo {

    @NotNull
    private UUID id;
    @NotEmpty
    private String shortLink;
    @NotNull
    private Long openingCount;
    @NotEmpty
    private String link;
    @NotNull
    private LocalDateTime endTime;
    @Nullable
    private String description;
    @NotNull
    private Boolean isActive;

    public LinkInfo copy() {
        return new LinkInfo(
                this.getId(),
                this.getShortLink(),
                this.getOpeningCount(),
                this.getLink(),
                this.getEndTime(),
                this.getDescription(),
                this.getIsActive()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkInfo linkInfo = (LinkInfo) o;

        return id.equals(linkInfo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
