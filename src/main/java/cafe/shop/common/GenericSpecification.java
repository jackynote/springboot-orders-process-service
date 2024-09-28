package cafe.shop.common;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GenericSpecification<T> implements Specification<T> {
    private final Map<String, Object> params;
    private final Pageable pageable;

    public GenericSpecification(Map<String, Object> params, Pageable pageable) {
        this.params = params;
        this.pageable = pageable;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if (value != null) {
                String[] keyParts = key.split("\\.");
                Path<Object> path = null;
                Join<?, ?> join = null;
                for (int i = 0; i < keyParts.length; i++) {
                    key = keyParts[i];
                    if (keyParts.length - 1 == i) path = join == null ? root.get(key) : join.get(key);
                    else join = join == null ? root.join(key) : join.join(key);
                }
                if ("id".equals(key)) value = UUID.fromString(value.toString());
                predicates.add(criteriaBuilder.equal(path, value));

            }
        }
        
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        
        if (pageable.isPaged()) {
            query.orderBy(getOrder(root, query, criteriaBuilder));
        }
        
        return query.getRestriction();
    }

    private List<Order> getOrder(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new ArrayList<>();
        for (Sort.Order sortOrder : pageable.getSort()) {
            Path<Object> expression = root.get(sortOrder.getProperty());
            orders.add(sortOrder.isAscending() ? criteriaBuilder.asc(expression) : criteriaBuilder.desc(expression));
        }
        return orders;
    }
}