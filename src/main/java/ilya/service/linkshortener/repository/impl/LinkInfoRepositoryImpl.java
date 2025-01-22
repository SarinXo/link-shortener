package ilya.service.linkshortener.repository.impl;


import ilya.service.linkshortener.dto.service.LinkInfoFilterDto;
import ilya.service.linkshortener.model.LinkInfoEntity;
import ilya.service.linkshortener.model.LinkInfoEntity_;
import ilya.service.linkshortener.repository.LinkInfoFilterRepository;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoFilterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    //todo приделать Slice к запросу
    /**
     * Данный запрос работает только с postgreSQL из-за функции ILIKE
     * @param filterDto dto с параметрами фильтрации
     * @return список фильтрованных entity
     */
    @Override
    public List<LinkInfoEntity> findByFilter(LinkInfoFilterDto filterDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LinkInfoEntity> query = cb.createQuery(LinkInfoEntity.class);
        Root<LinkInfoEntity> root = query.from(LinkInfoEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filterDto.linkPart())) {
            predicates.add(cb.isTrue(
                    cb.like(
                            cb.lower(root.get(LinkInfoEntity_.LINK)),
                            cb.lower(cb.literal("%" + filterDto.linkPart() + "%"))
                    )
            ));
        }
        if (StringUtils.hasText(filterDto.descriptionPart())) {
            predicates.add(cb.isTrue(
                    cb.like(
                            cb.lower(root.get(LinkInfoEntity_.DESCRIPTION)),
                            cb.lower(cb.literal("%" + filterDto.descriptionPart() + "%"))
                    )
            ));
        }
        if (Objects.nonNull(filterDto.fromEndTime())) {
            predicates.add(cb.greaterThanOrEqualTo(
                    root.get(LinkInfoEntity_.END_TIME),
                    filterDto.fromEndTime()
            ));
        }
        if (Objects.nonNull(filterDto.toEndTime())) {
            predicates.add(cb.lessThanOrEqualTo(
                    root.get(LinkInfoEntity_.END_TIME),
                    filterDto.toEndTime()));
        }
        if (Objects.nonNull(filterDto.isActive())) {
            predicates.add(cb.equal(
                    root.get(LinkInfoEntity_.IS_ACTIVE),
                    filterDto.isActive()
            ));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

}
