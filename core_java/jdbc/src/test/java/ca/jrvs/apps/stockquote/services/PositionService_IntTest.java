package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.*;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PositionService_IntTest {
    DatabaseConnectionManager dcm;
    QuoteDAO<Quote, Integer> quoteDAO;
    PositionDAO<Position, Integer> positionDAO;
    QuoteService quoteService;
    PositionService positionService;
    @Before
    public void setUp() {

    }

    @Test
    public void sell() throws SQLException {
        // Arrange
        dcm = new DatabaseConnectionManager();
        Connection c = dcm.getConnection();
        quoteDAO = new QuoteDAO<>(c);
        positionDAO = new PositionDAO<>(c);
        quoteService = new QuoteService(c, PropertiesHelper.getProperties()[6]);
        positionService = new PositionService(c, quoteService);

        quoteDAO.deleteAll();

        Quote quote = new Quote();
        quote.setSymbol("AAPL");
        quote.setOpen(47.34);
        quote.setHigh(91.06);
        quote.setLow(14.22);
        quote.setPrice(51.11);
        quote.setVolume(15);
        quote.setLatestTradingDay(new java.sql.Date(System.currentTimeMillis()));
        quote.setPreviousClose(34.13);
        quote.setChange(quote.getOpen() - quote.getPreviousClose());
        quote.setChangePercent(String.valueOf(quote.getChange() * 100));
        quote.setTimestamp(Timestamp.from(Instant.now()));
        quoteService.saveQuote(quote);

        // Act
        positionService.sell(1);

        // Assert
        assertNull(positionDAO.findById(1).get().getSymbol());
    }

}