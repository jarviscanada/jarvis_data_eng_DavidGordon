package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Position;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionDao extends JpaRepository<Position, Integer> {
    @Query("SELECT account_id, ticker, sum(size) AS position FROM public.security_order WHERE status = 'FILLED' GROUP BY account_id, ticker")
    public List<Position> getAllPositions();
}
