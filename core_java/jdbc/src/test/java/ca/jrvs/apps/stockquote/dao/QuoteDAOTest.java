package ca.jrvs.apps.stockquote.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

public class QuoteDAOTest {
    @Before
    public void setUp() throws Exception {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "postgres",
                "postgres", "password");
        Connection connection = null;

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<>(connection);
        quoteDAO.deleteAll();
    }
    @Test
    public void save() {
        // Arrange
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "postgres",
                "postgres", "password");
        Connection connection = null;

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<>(connection);
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
        Quote savedQuote = quoteDAO.save(quote);

        // Assert
        assertEquals(savedQuote, quote);
    }

    @Test
    public void findBySymbol() {
        // Arrange
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "postgres",
                "postgres", "password");
        Connection connection = null;

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<>(connection);
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
        Optional<Quote> quoteOptional = quoteDAO.findBySymbol("AAPL");
        Quote foundQuote = null;

        if(quoteOptional.isPresent()) {
            foundQuote = quoteOptional.get();
        }

        // Assert
        assert foundQuote != null;
        assertEquals(quote.getSymbol(), "AAPL");
    }
}