package ca.jrvs.apps.stockquote.dao;

import java.sql.Connection;
import java.util.Optional;

public class PositionDAO<Position, Integer> implements CrudDao<Position, Integer> {
    protected final Connection connection;

    public PositionDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Optional<Position> findById(Integer integer) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Iterable<Position> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer integer) throws IllegalArgumentException {

    }

    @Override
    public void deleteAll() {

    }
}
