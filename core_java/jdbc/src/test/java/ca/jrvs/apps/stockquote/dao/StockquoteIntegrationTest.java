package ca.jrvs.apps.stockquote.dao;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

public class StockquoteIntegrationTest {
    private static Connection connection;
    @Before
    public void setUp() throws Exception {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "postgres",
                "postgres", "password");

        connection = dcm.getConnection();

        QuoteDAO<Position, Integer> quoteDAO = new QuoteDAO<>(connection);
        PositionDAO<Position, Integer> positionDAO = new PositionDAO<>(connection);

        quoteDAO.deleteAll();
        positionDAO.deleteAll();
    }

    @Test
    public void testQuoteApp() {
        // Test will call QuoteHttpHelper to receive a quote from the API

        // Create a QuoteHttpHelper and fetch a quote
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("YOUR_API_KEY");
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

        // Create a Position for AAPL stock
        PositionDAO<Position, Integer> positionDAO = new PositionDAO<>(connection);
        Position position = new Position();

        position.setSymbol("AAPL");
        position.setNumOfShares(47);
        position.setValuePaid(1027.44);

        Position savedPosition = positionDAO.save(position);

        assertEquals(savedPosition.getSymbol(), "AAPL");
        assertEquals(quote.getSymbol(), "AAPL");
    }
}
