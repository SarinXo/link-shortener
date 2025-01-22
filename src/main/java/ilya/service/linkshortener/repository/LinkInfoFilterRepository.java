package ilya.service.linkshortener.repository;

import ilya.service.linkshortener.dto.service.LinkInfoFilterDto;
import ilya.service.linkshortener.model.LinkInfoEntity;

import java.util.List;

public interface LinkInfoFilterRepository {

    List<LinkInfoEntity> findByFilter(LinkInfoFilterDto filterDto);
}
