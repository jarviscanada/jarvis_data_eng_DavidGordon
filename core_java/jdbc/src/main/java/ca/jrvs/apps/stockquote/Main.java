package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.controllers.StockQuoteController;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        StockQuoteController stockQuoteController = new StockQuoteController();
        stockQuoteController.initClient();
    }
}
