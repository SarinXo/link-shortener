package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.model.LinkInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository extends JpaRepository<LinkInfoEntity, UUID> {

    Optional<LinkInfoEntity> findByShortLink(String shortLink);

}
