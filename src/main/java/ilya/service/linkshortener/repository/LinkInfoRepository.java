package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.model.LinkInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository extends JpaRepository<LinkInfoEntity, UUID>, LinkInfoFilterRepository {

    @Query(value = """
            SELECT *
            FROM link_info
            WHERE short_link = :shortLink
              AND is_active = true
              AND (end_time IS NULL OR link_info.end_time >= CURRENT_TIMESTAMP)
            """, nativeQuery = true)
    Optional<LinkInfoEntity> findActiveLinkByShortLink(String shortLink);

    @Query("""
            UPDATE LinkInfoEntity
            SET openingCount = openingCount + 1
            WHERE shortLink = :shortLink
            """)
    @Modifying
    @Transactional
    void incrementOpeningCountByShortLink(String shortLink);

}
