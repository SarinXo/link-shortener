package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.model.LinkInfoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;

public interface LinkInfoSliceRepository {

    Slice<LinkInfoEntity> findAllBy(Specification<LinkInfoEntity> spec, Pageable pageable);
}
