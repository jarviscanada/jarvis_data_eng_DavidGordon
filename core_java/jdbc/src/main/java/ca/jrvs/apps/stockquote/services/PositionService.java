package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

public class PositionService {
    final Logger logger = LoggerFactory.getLogger(PositionService.class);
    private final QuoteService quoteService;
    private final PositionDAO<Position, Integer> positionDAO;

    public PositionService(Connection connection, QuoteService quoteService) {
        positionDAO = new PositionDAO<Position, Integer>(connection);
        this.quoteService = quoteService;
    }

    public Position buy(String symbol, int numberOfShares, double price) {
        logger.info(new Timestamp(new Date().getTime()) + ": User buying position for stock " + symbol);
        // Store the quote info in the quote table before adding a position IF does not exist
        Optional<Quote> quoteOptional = quoteService.findQuoteBySymbol(symbol);

        // We don't have this quote in our table yet, so we will fetch it from the API
        // We check if the symbol exists in our controller, so using .get() here is safe
        if(quoteOptional.isEmpty() || quoteOptional.get().getSymbol() == null) {
            Quote quote = quoteService.fetchQuoteDataFromAPI(symbol).get();
            quoteService.saveQuote(quote);
        }

        Position position = new Position();
        position.setSymbol(symbol);
        position.setNumOfShares(numberOfShares);
        position.setValuePaid(price);
        return positionDAO.save(position);
    }

    public void sell(int id) {
        logger.info(new Timestamp(new Date().getTime()) + ": User selling position with id " + id);
        Optional<Position> positionOptional = positionDAO.findById(id);
        if(positionOptional.isEmpty()) {
            return;
        }

        Position position = positionOptional.get();

        positionDAO.deleteById(position.getId());
    }
}
