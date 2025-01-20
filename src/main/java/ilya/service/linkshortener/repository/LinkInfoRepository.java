package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.service.LinkInfoFilterDto;
import ilya.service.linkshortener.model.LinkInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository extends JpaRepository<LinkInfoEntity, UUID> {

    @Query("""
           FROM LinkInfoEntity
           WHERE shortLink = :shortLink
             AND isActive = true
             AND (endTime IS NULL OR endTime >= :#{T(java.time.LocalDateTime).now()})
           """)
    Optional<LinkInfoEntity> findActiveLinkByShortLink(String shortLink);

    @Query("""
           UPDATE LinkInfoEntity
           SET openingCount = openingCount + 1
           WHERE shortLink = :shortLink
           """)
    @Modifying
    @Transactional
    void incrementOpeningCountByShortLink(String shortLink);

    @Query(value = """
           from LinkInfoEntity
           """)
    List<LinkInfoEntity> findByFilter(LinkInfoFilterDto filterDto);
/*FROM LinkInfoEntity l
           WHERE (:#{filterDto.linkPart()}        IS NULL OR LOWER(l.link)        LIKE LOWER(CONCAT('%', :#{#filterDto.linkPart()} '%')))
             AND (:#{filterDto.descriptionPart()} IS NULL OR LOWER(l.description) LIKE LOWER(CONCAT('%', :#{filterDto.descriptionPart()} '%')))
             AND (:#{filterDto.fromEndTime()}     IS NULL OR l.end_time >= :#{filterDto.fromEndTime()})
             AND (:#{filterDto.toEndTime()}       IS NULL OR l.end_time <= :#{filterDto.toEndTime()})
             AND (:#{filterDto.isActive()}        IS NULL OR l.is_active = :#{filterDto.isActive()})
           )*/
}
