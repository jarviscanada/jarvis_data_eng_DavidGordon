package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Quote;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao"})
class QuoteDaoTest {
    @Autowired
    private QuoteDao quoteDao;

    @Test
    void saveOne() {
        Quote savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setTicker("aapl");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
    }

    @Test
    void deleteOne() {
        quoteDao.deleteById("aapl");
    }
}