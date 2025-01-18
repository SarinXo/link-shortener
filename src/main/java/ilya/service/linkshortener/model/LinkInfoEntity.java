package ilya.service.linkshortener.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = LinkInfoEntity.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class LinkInfoEntity extends AuditableEntity {

    public static final String TABLE_NAME = "link_info";

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotEmpty
    @Column(name = "short_link")
    private String shortLink;
    @NotNull
    @Column(name = "opening_count")
    private Long openingCount;
    @NotEmpty
    private String link;
    @NotNull
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Nullable
    private String description;
    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkInfoEntity linkInfo = (LinkInfoEntity) o;

        return id.equals(linkInfo.id);
    }

    @Override
    public int hashCode() {
        return id != null
                ? id.hashCode()
                : 31;
    }

}
