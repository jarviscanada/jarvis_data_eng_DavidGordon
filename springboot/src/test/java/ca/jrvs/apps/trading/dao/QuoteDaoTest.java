package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {TestConfig.class})
class QuoteDaoTest {
    @Autowired
    private QuoteDao quoteDao;

    @Test
    void saveOne() {
        // Arrange
        Quote quote = new Quote();
        quote.setAskPrice(10d);
        quote.setAskSize(10);
        quote.setBidPrice(10.2d);
        quote.setTicker("MSFT");
        quote.setLastPrice(10.1d);

        // Act
        Quote savedQuote = quoteDao.save(quote);

        // Assert
        assertEquals(quote, savedQuote);
    }

    @Test
    void deleteOne() {
        quoteDao.deleteById("MSFT");
    }
}