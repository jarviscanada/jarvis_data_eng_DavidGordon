package ca.jrvs.apps.trading.model;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

class MarketDataDaoTest {
    private MarketDataDao dao;

    @Before
    @Autowired
    public void setup(MarketDataDao dao) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/stable/");
        marketDataConfig.setToken(System.getenv("IEX_TOKEN"));
        this.dao = dao;
    }

    @Test
    void findById() throws IOException {
        // Arrange
        Optional<IexQuote> quoteOptional = Optional.empty();
        String expected = "AAPL";

        // Act
        quoteOptional = dao.findById("AAPL");

        IexQuote result = new IexQuote();
        if(quoteOptional.isPresent()) {
            result = quoteOptional.get();
        }

        assertNotNull(result);
    }
}