package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.*;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class QuoteService {
    private Connection connection;
    private QuoteDAO quoteDAO;
    private QuoteHttpHelper httpHelper;

    public QuoteService() {
        String[] properties = PropertiesHelper.getProperties();
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        quoteDAO = new QuoteDAO<Quote, Integer>(connection);
        httpHelper = new QuoteHttpHelper(properties[6]);
    }

    /**
     * Fetches latest quote data from endpoint
     * @param symbol The symbol of the stock to fetch
     * @return Latest quote information or empty optional if symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        return null;
    }
}
