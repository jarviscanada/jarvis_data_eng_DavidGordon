package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.controllers.StockQuoteController;
import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String apiKey = PropertiesHelper.getProperties()[6];
        StockQuoteController stockQuoteController = new StockQuoteController();
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();
        try {
            Connection connection = dcm.getConnection();
            stockQuoteController.initClient(connection, apiKey);
        } catch (SQLException e) {
            // logger.error("Could not open connection to database");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
