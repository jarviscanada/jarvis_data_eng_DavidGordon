package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.util.PropertiesHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

public class QuoteDAO_IntTest {
    private static Connection connection;
    @Before
    public void setUp() throws Exception {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();

        connection = dcm.getConnection();

        QuoteDAO<Position, Integer> quoteDAO = new QuoteDAO<>(connection);
        PositionDAO<Position, Integer> positionDAO = new PositionDAO<>(connection);

        quoteDAO.deleteAll();
        positionDAO.deleteAll();
    }

    @Test
    public void save() {
        // Arrange
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        Connection connection = null;

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<>(connection);
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


        // Act
        Quote savedQuote = quoteDAO.save(quote);

        // Assert
        assertEquals(savedQuote, quote);
    }

    @Test
    public void findBySymbol() {
        // Test will call QuoteHttpHelper to receive a quote from the API
        String[] properties  = PropertiesHelper.getProperties();
        String apiKey = properties[6];

        // Create a QuoteHttpHelper and fetch a quote
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(apiKey);
        Quote quote = quoteHttpHelper.fetchQuoteInfo("AAPL");

        // Save the quote
        QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<>(connection);
        quote.setTimestamp(Timestamp.from(Instant.now()));
        quoteDAO.save(quote);

        // Find it in the database
        Optional<Quote> quoteOptional = quoteDAO.findBySymbol(quote.getSymbol());

        if(quoteOptional.isPresent()) {
            quote = quoteOptional.get();
        }
    }
}