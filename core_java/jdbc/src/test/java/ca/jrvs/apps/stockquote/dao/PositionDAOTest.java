package ca.jrvs.apps.stockquote.dao;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

public class PositionDAOTest {
    @Test
    public void save() {
        // Arrange
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        Connection connection = null;

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        PositionDAO<Position, Integer> positionDAO = new PositionDAO<>(connection);
        positionDAO.deleteAll();

        Position position = new Position();
        position.setSymbol("AAPL");
        position.setNumOfShares(47);
        position.setValuePaid(1027.44);

        QuoteDAO<Quote, Integer> quoteDAO = new QuoteDAO<>(connection);
        quoteDAO.deleteAll();

        Quote quote = new Quote();

        quote.setSymbol("AAPL");
        quote.setOpen(47.34);
        quote.setHigh(91.06);
        quote.setLow(14.22);
        quote.setPrice(51.11);
        quote.setVolume(15);
        quote.setLatestTradingDay(new java.sql.Date(System.currentTimeMillis()));
        quote.setPreviousClose(34.13);
        quote.setChange(quote.getOpen() - quote.getPreviousClose());
        quote.setChangePercent(String.valueOf(quote.getChange() * 100));
        quote.setTimestamp(Timestamp.from(Instant.now()));

        quoteDAO.save(quote);

        // Act
        Position savedPosition = positionDAO.save(position);

        // Assert
        assertEquals(savedPosition, position);
    }

    @Test
    public void findBySymbol() {
        // Arrange
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        Connection connection = null;

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        PositionDAO<Position, Integer> positionDAO = new PositionDAO<>(connection);
        Position position = new Position();

        position.setSymbol("AAPL");
        position.setNumOfShares(47);
        position.setValuePaid(1027.44);

        // Act
        Optional<Position> positionOptional = positionDAO.findBySymbol("AAPL");
        Position foundPosition = null;

        if(positionOptional.isPresent()) {
            foundPosition = positionOptional.get();
        }

        // Assert
        assert foundPosition != null;
        assertEquals(position.getSymbol(), "AAPL");
    }
}