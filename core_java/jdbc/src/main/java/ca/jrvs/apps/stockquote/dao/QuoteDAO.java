package ca.jrvs.apps.stockquote.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.simple.SimpleLoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class QuoteDAO<Quote, Integer> implements CrudDao<Quote, Integer> {
    final Logger logger = LoggerFactory.getLogger(QuoteDAO.class);
    protected final Connection connection;
    private static final String SAVE = "INSERT INTO quote " +
            "(symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_BY_ID = "SELECT * FROM quote WHERE id = ?";
    private static final String FIND_BY_SYMBOL = "SELECT * FROM quote WHERE symbol = ?";
    private static final String FIND_ALL = "SELECT * FROM quote";
    private static final String DELETE = "DELETE FROM quote WHERE id = ?";
    private static final String DELETE_ALL = "TRUNCATE TABLE quote CASCADE";

    public QuoteDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {
        if(entity == null) {
            logger.error("Provided quote was null");
            throw new IllegalArgumentException();
        }

        ca.jrvs.apps.stockquote.dao.Quote quote = (ca.jrvs.apps.stockquote.dao.Quote) entity;

        try (PreparedStatement statement = this.connection.prepareStatement(SAVE)) {
            statement.setString(1, quote.getSymbol());
            statement.setDouble(2, quote.getOpen());
            statement.setDouble(3, quote.getHigh());
            statement.setDouble(4, quote.getLow());
            statement.setDouble(5, quote.getPrice());
            statement.setInt(6, quote.getVolume());
            statement.setDate(7, quote.getLatestTradingDay());
            statement.setDouble(8, quote.getPreviousClose());
            statement.setDouble(9, quote.getChange());
            statement.setString(10, quote.getChangePercent());
            statement.setTimestamp(11, new Timestamp(new Date().getTime()));
            statement.execute();

            return entity;
        } catch (SQLException e) {
            logger.error("There was an error saving the quote - " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Quote> findById(Integer integer) throws IllegalArgumentException {
        if(integer == null) {
            logger.error("Provided quote was null");
        }

        ca.jrvs.apps.stockquote.dao.Quote quote = new ca.jrvs.apps.stockquote.dao.Quote();

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, (java.lang.Integer) integer);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                quote.setId(resultSet.getInt(1));
                quote.setSymbol(resultSet.getString(2));
                quote.setOpen(resultSet.getDouble(3));
                quote.setHigh(resultSet.getDouble(4));
                quote.setLow(resultSet.getDouble(5));
                quote.setPrice(resultSet.getDouble(6));
                quote.setVolume(resultSet.getInt(7));
                quote.setLatestTradingDay(resultSet.getDate(8));
                quote.setPreviousClose(resultSet.getDouble(9));
                quote.setChange(resultSet.getDouble(10));
                quote.setChangePercent(resultSet.getString(11));
                quote.setTimestamp(resultSet.getTimestamp(12));
            }

            return Optional.of((Quote)quote);
        } catch (SQLException e) {
            logger.error("There was an error querying the database - " + e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Quote> findBySymbol(String symbol) throws IllegalArgumentException {
        if(symbol == null) {
            logger.error("Provided quote was null");
        }

        ca.jrvs.apps.stockquote.dao.Quote quote = new ca.jrvs.apps.stockquote.dao.Quote();

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_SYMBOL)) {
            statement.setString(1, symbol);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                quote.setId(resultSet.getInt(1));
                quote.setSymbol(resultSet.getString(2));
                quote.setOpen(resultSet.getDouble(3));
                quote.setHigh(resultSet.getDouble(4));
                quote.setLow(resultSet.getDouble(5));
                quote.setPrice(resultSet.getDouble(6));
                quote.setVolume(resultSet.getInt(7));
                quote.setLatestTradingDay(resultSet.getDate(8));
                quote.setPreviousClose(resultSet.getDouble(9));
                quote.setChange(resultSet.getDouble(10));
                quote.setChangePercent(resultSet.getString(11));
                quote.setTimestamp(resultSet.getTimestamp(12));
            }

            return Optional.of((Quote)quote);
        } catch (SQLException e) {
            logger.error("There was an error querying the database - " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> quotes = new ArrayList<Quote>();
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ca.jrvs.apps.stockquote.dao.Quote quote = new ca.jrvs.apps.stockquote.dao.Quote();
                quote.setId(resultSet.getInt(1));
                quote.setSymbol(resultSet.getString(2));
                quote.setOpen(resultSet.getDouble(3));
                quote.setHigh(resultSet.getDouble(4));
                quote.setLow(resultSet.getDouble(5));
                quote.setPrice(resultSet.getDouble(6));
                quote.setLatestTradingDay(resultSet.getDate(7));
                quote.setPreviousClose(resultSet.getDouble(8));
                quote.setChange(resultSet.getDouble(9));
                quote.setChangePercent(resultSet.getString(10));
                quote.setTimestamp(resultSet.getTimestamp(11));
                quotes.add((Quote)quote);
            }

            return quotes;
        } catch (SQLException e) {
            logger.error("There was an error querying the database - " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer integer) throws IllegalArgumentException {
        if(integer == null) {
            logger.error("Provided quote was null");
        }

        try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
            statement.setInt(1, (int)integer);
            statement.execute();
        } catch (SQLException e) {
            logger.error("There was an error querying the database - " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
