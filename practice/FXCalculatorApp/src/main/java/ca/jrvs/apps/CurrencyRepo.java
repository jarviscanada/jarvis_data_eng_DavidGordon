package ca.jrvs.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CurrencyRepo {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyRepo.class);
    private Connection c;

    public CurrencyRepo(Connection c) {
        logger.info("Initializing CurrencyRepo");
        this.c = c;
    }

    public Optional<Currency> getCurrency(String code) {
        logger.info("Retrieving Currency from database");
        try (PreparedStatement s = c.prepareStatement("SELECT * FROM currency WHERE symbol=?")) {
            s.setString(1, code);
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Currency curr = new Currency();
                curr.setCode(code);
                curr.setName(rs.getString("name"));
                return Optional.of(curr);
            }
        } catch (SQLException e) {
            logger.info("Error retrieving Currency from database: " + e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

}
