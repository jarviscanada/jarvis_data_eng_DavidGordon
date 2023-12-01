package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Position;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionDao extends JpaRepository<Position, Integer> {

}
