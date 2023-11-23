package ca.jrvs.apps.trading.model;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class MarketDataDaoTest {
    @Autowired
    MarketDataDao dao;

    @Test
    void saveQuoteById() throws IOException {
        // Arrange
        IexQuote quote = new IexQuote();

        // Act
        IexQuote savedQuote = dao.save(quote);

        // Assert
        assertEquals(quote, savedQuote);
    }

    @Test
    void findByAllId() throws IOException {
        // Arrange
        List<IexQuote> quoteList = Collections.emptyList();

        // Act
        quoteList = dao.findAllById(Arrays.asList("AAPL", "asd"));

        // Assert
        assertNotNull(quoteList);
    }
}