package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    @Autowired
    private final MarketDataHttpHelper marketDataHttpHelper;

    public QuoteService(MarketDataHttpHelper marketDataHttpHelper) {
        logger.debug("Creating QuoteService");
        this.marketDataHttpHelper = marketDataHttpHelper;
    }

    /**
     * Find an IexQuote from the given ticker
     *
     * @param ticker the stock ticker
     * @return corresponding IexQuote object
     * @throws IllegalArgumentExcpetion if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        logger.info("Fetching quote from IEX");

        Optional<IexQuote> iexQuoteOptional = marketDataHttpHelper.getIexQuote(ticker);
        if(iexQuoteOptional.isPresent()) {
            return iexQuoteOptional.get();
        }

        throw new IllegalArgumentException("Ticker is invalid");
    }

    /**
     * Update quote table against IEX source
     *
     * - get all quotes from the db
     * - for each ticker get IexQuote
     * - convert IexQuote to Quote entity
     * - persist quote to db
     *
     * @throws ResourceNotFoundException if ticker is not found from IEX
     * @throws DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        return;
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     *
     * - get IexQuote(s)
     * - convert each IexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers list of quote tickers to save
     * @return list of converted quote entities
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        return null;
    }

    /**
     * Update a given quote to the quote table without validation
     *
     * @param quote entity to save
     * @return the saved quote entity
     */
    public Quote saveQuote(Quote quote) {
        return null;
    }

    /**
     * Find all quotes from the quote table
     *
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return null;
    }

    /**
     * Helper method to map an IexQuote to a Quote entity
     * Note: 'iexQuote.getLatestPrice() == null' if the stock market is closed
     * Make sure to set a default value for number field(s)
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        return null;
    }
}
