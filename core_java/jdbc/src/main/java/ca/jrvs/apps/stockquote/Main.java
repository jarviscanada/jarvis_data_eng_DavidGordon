package ca.jrvs.apps.stockquote;

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
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("YOUR_API_KEY");
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "postgres",
                "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<Quote, Integer>(connection);
            /*
            Quote quote = quoteHttpHelper.fetchQuoteInfo("AAPL");
            quote.setTimestamp(Timestamp.from(Instant.now()));
            quoteDAO.save(quote);
             */
            Quote quote = quoteDAO.findById(1).get();
            System.out.println(quote.getSymbol());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
