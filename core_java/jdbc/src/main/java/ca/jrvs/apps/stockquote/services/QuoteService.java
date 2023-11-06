package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.*;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public class QuoteService {
    final Logger logger = LoggerFactory.getLogger(QuoteService.class);
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
        logger.info(new Timestamp(new Date().getTime()) + ": Fetching quote from Alpha Vantage");
        return Optional.ofNullable(quoteHttpHelper.fetchQuoteInfo(symbol));
    }

    public Optional<Quote> saveQuote(Quote quote) {
        logger.info(new Timestamp(new Date().getTime()) + ": Saving quote to database");
        return Optional.ofNullable(quoteDAO.save(quote));
    }

    public Optional<Quote> findQuoteBySymbol(String symbol) {
        logger.info(new Timestamp(new Date().getTime()) + ": Querying for quote with symbol " + symbol);
        return quoteDAO.findBySymbol(symbol);
    }
}
