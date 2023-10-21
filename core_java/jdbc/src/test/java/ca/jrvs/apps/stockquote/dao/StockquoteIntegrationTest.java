package ca.jrvs.apps.stockquote.dao;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;

public class StockquoteIntegrationTest {
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
    public void testQuoteApp() {
        // Test will call QuoteHttpHelper to receive a quote from the API
        String apiKey = "";
        String[] properties = new String[7];
        try {
            File propertiesFile = Paths.get(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("properties.txt")).toURI()).toFile();

            Reader reader = new FileReader(propertiesFile.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            int i = 0;
            while(i < 7) {
                properties[i] = bufferedReader.readLine().split(":")[1];
                i++;
            }
            apiKey = properties[6];
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

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
