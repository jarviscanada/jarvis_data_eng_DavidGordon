package ca.jrvs.apps.stockquote.controllers;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.services.PositionService;
import ca.jrvs.apps.stockquote.services.QuoteService;

public class StockQuoteController {
    private QuoteService quoteService;
    private PositionService positionService;

    /**
     * User interface for our application
     */
    public void initClient() {
        quoteService = new QuoteService();
        positionService = new PositionService();
    }

    public Quote getQuote(String symbol) {
        return null;
    }

    public Position buyPosition() {
        return null;
    }

    public void sellPosition() {

    }
}
