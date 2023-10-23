package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.*;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class QuoteService {
    private final QuoteDAO<Quote, Integer> quoteDAO;
    private final QuoteHttpHelper quoteHttpHelper;

    public QuoteService(Connection connection, String apiKey) {
        quoteDAO = new QuoteDAO<Quote, Integer>(connection);
        quoteHttpHelper = new QuoteHttpHelper(apiKey);
    }

    /**
     * Fetches latest quote data from endpoint
     * @param symbol The symbol of the stock to fetch
     * @return Latest quote information or empty optional if symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        return Optional.ofNullable(quoteHttpHelper.fetchQuoteInfo(symbol));
    }
}
