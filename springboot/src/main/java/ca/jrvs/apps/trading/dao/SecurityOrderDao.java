package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.SecurityOrder;
import org.hibernate.validator.internal.engine.resolver.JPATraversableResolver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityOrderDao extends JpaRepository<SecurityOrder, Integer> {
}
