package ca.jrvs.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;

public class DailyRateRepo {
    private static final Logger logger = LoggerFactory.getLogger(DailyRateRepo.class);
    private Connection c;

    public DailyRateRepo(Connection c) {
        logger.info("Initializing DailyRateRepo");
        this.c = c;
    }

    public Optional<Rate> getRate(Date date, String fromCode, String toCode) {
        logger.info("Retrieving Rate from database");
        logger.info("Saving Rate to database");

        String FIND_RATE = "SELECT * FROM daily_rate WHERE day = ? AND from_symbol = ? AND to_symbol = ?";
        try(PreparedStatement statement = this.c.prepareStatement(FIND_RATE)) {
            statement.setDate(1, date);
            statement.setString(2, fromCode);
            statement.setString(3, toCode);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Rate rate = new Rate();
                rate.setDate(resultSet.getDate(1));
                rate.setFromSymbol(resultSet.getString(2));
                rate.setToSymbol(resultSet.getString(3));
                rate.setOpen(resultSet.getDouble(4));
                rate.setHigh(resultSet.getDouble(5));
                rate.setLow(resultSet.getDouble(6));
                rate.setClose(resultSet.getDouble(7));
                return Optional.of(rate);
            }
        } catch (SQLException e) {
            logger.error("Couldn't save Rate to database: " + e);
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public void save(Rate r) {
        logger.info("Saving Rate to database");

        if(r == null) {
            logger.error("Couldn't save rate. Rate was null");
            throw new IllegalArgumentException();
        }

        String SAVE_RATE = "INSERT INTO daily_rate (day, from_symbol, to_symbol, open, high, low, close) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = this.c.prepareStatement(SAVE_RATE)) {
            statement.setDate(1, r.getDate());
            statement.setString(2, r.getFromSymbol());
            statement.setString(3, r.getToSymbol());
            statement.setDouble(4, r.getOpen());
            statement.setDouble(5, r.getHigh());
            statement.setDouble(6, r.getLow());
            statement.setDouble(7, r.getClose());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Couldn't save Rate to database: " + e);
            throw new RuntimeException(e);
        }
    }

}