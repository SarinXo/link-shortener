package ilya.service.linkshortener.repository.impl;

import ilya.service.linkshortener.model.LinkInfoEntity;
import ilya.service.linkshortener.repository.LinkInfoSliceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LinkInfoSliceRepositoryImpl implements LinkInfoSliceRepository {

    private final EntityManager em;


    public LinkInfoSliceRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Slice<LinkInfoEntity> findAllBy(Specification<LinkInfoEntity> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LinkInfoEntity> query = cb.createQuery(LinkInfoEntity.class);
        Root<LinkInfoEntity> root = query.from(LinkInfoEntity.class);

        Predicate predicate = spec.toPredicate(root, query, cb);
        query.where(predicate);

        TypedQuery<LinkInfoEntity> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize() + 1);

        List<LinkInfoEntity> resultList = typedQuery.getResultList();

        boolean hasNext = resultList.size() > pageable.getPageSize();
        if (hasNext) {
            resultList.remove(resultList.size() - 1);
        }

        return new SliceImpl<>(resultList, pageable, hasNext);
    }
}
