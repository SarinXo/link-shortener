package ilya.service.linkshortener.repository.specification;


import ilya.service.linkshortener.model.LinkInfoEntity;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class LinkInfoSpecificationFilter {

    public static Specification<LinkInfoEntity> isFieldInclude(String field, @Nullable String part) {
        return (root, query, cb) -> StringUtils.hasText(part)
                ? cb.isTrue(
                        cb.like(
                                cb.lower(root.get(field)),
                                cb.lower(cb.literal("%" + part + "%"))
                ))
                : cb.isTrue(cb.literal(true)); // skip
    }

    public static Specification<LinkInfoEntity> beforeOrEqualTime(String field, @Nullable LocalDateTime time) {
        return (root, query, cb) -> Objects.nonNull(time)
                ? cb.lessThanOrEqualTo(root.get(field), time)
                : cb.isTrue(cb.literal(true)); // skip
    }

    public static Specification<LinkInfoEntity> afterOrEqualTime(String field, @Nullable LocalDateTime time) {
        return (root, query, cb) -> Objects.nonNull(time)
                ? cb.greaterThanOrEqualTo(root.get(field), time)
                : cb.isTrue(cb.literal(true)); // skip
    }

    //todo подумать о типобезопасности, возможно через генерируемые Entity_ классы можно обыграть
    public static Specification<LinkInfoEntity> isEqual(String field, @Nullable Object value) {
        return (root, query, cb) -> Objects.nonNull(value)
                ? cb.equal(root.get(field), value)
                : cb.isTrue(cb.literal(true)); // skip
    }

}
