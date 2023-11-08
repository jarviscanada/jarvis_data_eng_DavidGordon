package ca.jrvs.apps.stockquote.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

public class QuoteDAO_UnitTest {
    QuoteDAO<Quote, Integer> quoteDAO;
    @Mock
    DatabaseConnectionManager dcm;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save() throws SQLException {
        // Arrange
        Mockito.when(dcm.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement("INSERT INTO quote (symbol, open, high, low, price, volume, " +
                "latest_trading_day, previous_close, change, change_percent, timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))
                .thenReturn(statement);

        Connection c = dcm.getConnection();
        quoteDAO = new QuoteDAO<Quote, Integer>(c);

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

        // Act
        Quote savedQuote = (Quote) quoteDAO.save(quote);

        // Assert
        assertEquals(savedQuote, quote);
    }
}