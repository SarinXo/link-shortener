package ilya.service.linkshortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static ilya.service.linkshortener.util.Constants.APP_USER;

@Getter
@Setter
@MappedSuperclass
public class AuditableEntity {

    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    @Column(name = "last_update_user")
    private String lastUpdateUser;

    @PrePersist
    public void prePersist() {
        var now = LocalDateTime.now();
        this.createTime = now;
        this.lastUpdateTime = now;
        this.createUser = APP_USER;
        this.lastUpdateUser = APP_USER;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateUser = APP_USER;
        this.lastUpdateTime = LocalDateTime.now();
    }
}
