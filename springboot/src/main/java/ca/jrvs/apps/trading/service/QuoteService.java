package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import ca.jrvs.apps.trading.model.IexQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    @Autowired
    private final MarketDataHttpHelper marketDataHttpHelper;

    public QuoteService(MarketDataHttpHelper marketDataHttpHelper) {
        logger.debug("Creating QuoteService");
        this.marketDataHttpHelper = marketDataHttpHelper;
    }
    public IexQuote findIexQuoteByTicker(String ticker) {
        logger.info("Fetching quote from IEX");
        return marketDataHttpHelper.getIexQuote(ticker).orElseThrow();
    }
}
