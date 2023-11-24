package ca.jrvs.apps.trading.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteDao extends JpaRepository<Quote, String> {
    Optional<Quote> findById(String ticker);
    List<Quote> findAllById(Iterable<String> tickers);
    boolean existsById(String s);
    List<Quote> findAll();
    long count();
    void deleteById(String s);
    void delete(Quote entity);
    void deleteAll(Iterable<? extends Quote> entities);
    void deleteAll();
    Quote save(Quote entity);
    List<Quote> saveAll(List<Quote> entities);
}