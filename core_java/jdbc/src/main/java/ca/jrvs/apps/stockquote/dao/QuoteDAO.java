package ca.jrvs.apps.stockquote.dao;

import java.util.Optional;

public class QuoteDAO<Quote, Integer> implements CrudDao<Quote, Integer> {
    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Optional<Quote> findById(Integer integer) throws IllegalArgumentException {
        return Optional.empty();
    }

    @Override
    public Iterable<Quote> findAll() {
        return null;
    }

    @Override
    public void deleteById(Object o) throws IllegalArgumentException {

    }

    @Override
    public void deleteAll() {

    }
}
