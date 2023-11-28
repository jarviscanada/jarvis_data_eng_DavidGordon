package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.Quote;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    @Autowired
    private final MarketDataHttpHelper marketDataHttpHelper;

    @Autowired
    private final QuoteDao quoteDao;

    public QuoteService(MarketDataHttpHelper marketDataHttpHelper, QuoteDao quoteDao) {
        logger.debug("Creating QuoteService");
        this.marketDataHttpHelper = marketDataHttpHelper;
        this.quoteDao = quoteDao;
    }

    /**
     * Find an IexQuote from the given ticker
     *
     * @param ticker the stock ticker
     * @return corresponding IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        logger.info("Fetching quote from IEX");

        Optional<IexQuote> iexQuoteOptional = marketDataHttpHelper.getIexQuote(ticker);
        if (iexQuoteOptional.isPresent()) {
            return iexQuoteOptional.get();
        }

        throw new IllegalArgumentException("Ticker is invalid");
    }

    /**
     * Update quote table against IEX source
     * - get all quotes from the db
     * - for each ticker get IexQuote
     * - convert IexQuote to Quote entity
     * - persist quote to db
     *
     * @throws DataAccessException      if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        try {
            List<Quote> quoteList = quoteDao.findAll();
            List<String> quoteTickers = new ArrayList<>();
            List<Quote> updatedQuoteList = new ArrayList<>();

            for (Quote q : quoteList) {
                quoteTickers.add(q.getTicker());
            }

            Optional<List<IexQuote>> iexQuoteListOptional = marketDataHttpHelper.getBatchQuote(quoteTickers);

            if (iexQuoteListOptional.isEmpty()) {
                throw new NotFoundException("Invalid tickers");
            }

            for (IexQuote iexQuote : iexQuoteListOptional.get()) {
                updatedQuoteList.add(buildQuoteFromIexQuote(iexQuote));
            }

            quoteDao.saveAll(updatedQuoteList);
        } catch (Exception e) {
            logger.error("Error updating market data: " + e.getMessage());
        }

        throw new IllegalArgumentException("Ticker is invalid");
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     * <p>
     * - get IexQuote(s)
     * - convert each IexQuote to Quote entity
     * - persist the quote to db
     *
     * @param tickers list of quote tickers to save
     * @return list of converted quote entities
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> quoteList = new ArrayList<>();
        try {
            Optional<List<IexQuote>> iexQuoteListOptional = marketDataHttpHelper.getBatchQuote(tickers);

            if (iexQuoteListOptional.isEmpty()) {
                throw new NotFoundException("Invalid tickers");
            }

            for (IexQuote iexQuote : iexQuoteListOptional.get()) {
                quoteList.add(buildQuoteFromIexQuote(iexQuote));
            }

            quoteDao.saveAll(quoteList);
        } catch (Exception e) {
            logger.error("Error saving quotes: " + e.getMessage());
        }
        return quoteList;
    }

    /**
     * Update a given quote to the quote table without validation
     *
     * @param quote entity to save
     * @return the saved quote entity
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table
     *
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }

    /**
     * Helper method to map an IexQuote to a Quote entity
     * Note: 'iexQuote.getLatestPrice() == null' if the stock market is closed
     * Make sure to set a default value for number field(s)
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setAskPrice(Double.valueOf(iexQuote.getIexAskPrice()));
        quote.setAskSize(iexQuote.getIexAskSize());
        quote.setBidPrice(Double.valueOf(iexQuote.getIexBidPrice()));
        quote.setBidSize(iexQuote.getIexBidSize());
        quote.setLastPrice(iexQuote.getIexClose());
        return quote;
    }
}
