package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class QuoteService_IntTest {
    DatabaseConnectionManager dcm;
    QuoteService quoteService;
    QuoteDAO<Quote, Integer> quoteDAO;

    @Test
    public void createQuote() throws SQLException {
        // Arrange
        dcm = new DatabaseConnectionManager();
        Connection c = dcm.getConnection();
        quoteDAO = new QuoteDAO<>(c);
        quoteDAO.deleteAll();
        quoteService = new QuoteService(c, PropertiesHelper.getProperties()[6]);

        // Act
        Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI("AAPL");

        if(quoteOptional.isPresent()) {
            quoteService.saveQuote(quoteOptional.get());
        }

        // Assert
        assertNotNull(quoteService.findQuoteBySymbol("AAPL"));
    }
}